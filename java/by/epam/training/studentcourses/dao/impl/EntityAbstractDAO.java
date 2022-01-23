package by.epam.training.studentcourses.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.dao.EntityDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.exception.DBErrorMessages;
import by.epam.training.studentcourses.dao.exception.InternalException;
import by.epam.training.studentcourses.dao.exception.InvalidEntityException;
import by.epam.training.studentcourses.dao.exception.InvalidRequestException;
import by.epam.training.studentcourses.dao.exception.NoSuchEntityException;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPool;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPoolFactory;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.Identifiable;
import by.epam.training.studentcourses.util.TableAttr;


public abstract class EntityAbstractDAO<T extends Identifiable> implements EntityDAO<T> {
	
    private static final Logger logger = LogManager.getLogger(EntityAbstractDAO.class);
	private String insertPrepStatement;
	private String deleteByIdPrepStatement;
	private TableAttr idAttr;
	private String tableName;
	private TableAttr[] tableAttributes;
	private ConnectionPool connectionPool;
	
	protected EntityAbstractDAO(String tableName, TableAttr[] tableAttributes, TableAttr idAttr) {
		this.tableName = tableName;
		this.tableAttributes = tableAttributes;
		this.idAttr = idAttr;
		insertPrepStatement = PrepStHelper.genInsertStatement(tableName, tableAttributes.length);
		deleteByIdPrepStatement = PrepStHelper.genDeleteByIdStatement(tableName, idAttr);
		this.connectionPool = ConnectionPoolFactory.getInstance();
	}

	@Override
	public void add(List<T> entityList) throws DAOException {
		if (entityList.isEmpty()) {
			return;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				conn = connectionPool.getConnection();
				ps = conn.prepareStatement(insertPrepStatement);
				for (T entity : entityList) {
					if (!validateEntityForInsert(entity)) {
						throw new InvalidEntityException(entity);
					}
					entity.setId(null);
					fillPrepStatementWithResultSet(entity, ps, false);
					logger.trace(ps);
					ps.addBatch();
				}
				ps.executeBatch();
				conn.commit();
			} finally {
				if (conn != null) {
					connectionPool.releaseConnection(conn);
				}
				if (ps != null) {
					ps.close();
				}
			}
		} catch(SQLException e) {
			throw new InternalException(e);
		}
	}

	@Override
	public List<T> getByFilter(Filter filter) throws DAOException {
		Connection conn = null;
		List<T> entityList = new ArrayList<T>();
		T entity;
		try {
			conn = connectionPool.getConnection();
			if (!validateFilter(filter, tableAttributes)) {
				throw new InvalidRequestException(
						DBErrorMessages.getFilterDoesntMatchTableMessage(tableName, filter),
						new IllegalArgumentException());
			}
			PreparedStatement ps = conn.prepareStatement(
					PrepStHelper.genSelectByFilterStatement(tableName, filter));
			PrepStHelper.fill(ps, true, filter);
			logger.trace(ps);
			ResultSet rs = ps.executeQuery();
			connectionPool.releaseConnection(conn);
			while (rs.next()) {
				entity = createEntityByResultSet(rs);
				entityList.add(entity);
			}
			return entityList;
		} catch (SQLException e) {
			throw new InternalException(e);
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
	}

	@Override
	public void update(List<T> entityList) throws DAOException {
		if (entityList.isEmpty()) {
			return;
		}
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = null;
			boolean[] nullAttributesStates;
			for (T entity : entityList) {
				if (entity.getId() == null) {
					conn.rollback();
					throw new InvalidEntityException(entity, 
							DBErrorMessages.genIdIsNotDefinedMessage(entity.getClass().getName()));
				}
				nullAttributesStates = getNullAttributesStates(entity);
				int nullAttrCount = 0;
				for (int i = 0; i < nullAttributesStates.length; i ++) {
					if (nullAttributesStates[i] && !tableAttributes[i].getAttrName().equals(idAttr.getAttrName())) {
						nullAttrCount ++;
					}
				}
				if (nullAttrCount == tableAttributes.length - 1) {
					continue;
				}
				ps = conn.prepareStatement(PrepStHelper.genUpdateByIdStatement(
						tableName, tableAttributes, nullAttributesStates, idAttr));
				fillPrepStatementWithResultSet(entity, ps, true);
				ps.setInt(ps.getParameterMetaData().getParameterCount(), entity.getId());
				logger.trace(ps);
				ps.addBatch();
			}
			if (ps != null) {
				ps.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			try { 
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				//LOGGER
			}
			throw new InternalException(e);
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}

	}

	@Override
	public void deleteByIdsListCascade(List<Integer> entitiesIdsList) throws DAOException {
		if (entitiesIdsList.isEmpty()) {
			return;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				conn = connectionPool.getConnection();
				ps = conn.prepareStatement(deleteByIdPrepStatement);
				for (Integer entitysId : entitiesIdsList) {
					if (entitysId == null) {
						conn.rollback();
						throw new InvalidRequestException(
								DBErrorMessages.genIdIsNotDefinedMessage(tableName),
								new NullPointerException());
					}
					ps.setInt(1, entitysId);
					logger.trace(ps);
					ps.addBatch();
				}
				ps.executeBatch();
				conn.commit(); //IT WORKS WITHOUT COMMIT!??? how
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					connectionPool.releaseConnection(conn);
				}
			}
		} catch (SQLException e) {
			throw new InternalException(e);
		}
	}
	
	@Override
	public void add(T entity) throws DAOException {
		add(Arrays.asList(entity));
	}
	
	@Override
	public T getById(Integer id) throws DAOException {
		List<T> entityList = getByFilter(
				new Filter(idAttr.getAttrName(), String.valueOf(id)));
		if (entityList.isEmpty()) {
			throw new NoSuchEntityException();
		}
		return entityList.get(0);
	}	
	
	@Override 
	public void update(T entity) throws DAOException {
		update(Arrays.asList(entity));
	}
	
	@Override
	public void deleteById(Integer id) throws DAOException {
		deleteByIdsListCascade(Arrays.asList(id));
	}
	
	private boolean validateFilter(Filter filter, TableAttr[] allowableAttributes) {
		for (int i = 0; i < filter.size(); i ++) {
			for (int j = 0; j < allowableAttributes.length; j ++) {
				if (filter.getAttrName(i).equals(allowableAttributes[i].getAttrName())) {
					break;
				}
				if (j == allowableAttributes.length) {
					return false;
				}
			}
		}
		return true;
	}
	
	public abstract boolean validateEntityForInsert(T entity);
	
	public abstract void fillPrepStatementWithResultSet(T entity, PreparedStatement ps, boolean skipNull) throws SQLException;
	
	public abstract T createEntityByResultSet(ResultSet rs) throws SQLException, DAOException;
	
	public abstract boolean[] getNullAttributesStates(T entity);
	

}
