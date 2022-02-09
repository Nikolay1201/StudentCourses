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
import by.epam.training.studentcourses.dao.exception.DBErrorMessages;
import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.dao.exception.InvalidEntityException;
import by.epam.training.studentcourses.dao.exception.InvalidRequestException;
import by.epam.training.studentcourses.dao.exception.NoSuchEntityException;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLErrorCodes;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPool;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPoolFactory;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.Identifiable;
import by.epam.training.studentcourses.util.TableAttr;

public abstract class EntityAbstractDAO<T extends Identifiable> implements EntityDAO<T> {

	private static final Logger log = LogManager.getLogger(EntityAbstractDAO.class);
	private final String insertPrepStatement;
	private final String deleteByIdPrepStatement;
	private final TableAttr idAttr;
	private final String tableName;
	private final TableAttr[] tableAttributes;
	private final ConnectionPool connectionPool;

	protected EntityAbstractDAO(String tableName, TableAttr[] tableAttributes, TableAttr idAttr) {
		this.tableName = tableName;
		this.tableAttributes = tableAttributes;
		this.idAttr = idAttr;
		insertPrepStatement = PrepStHelper.genInsertStatement(tableName, tableAttributes.length);
		deleteByIdPrepStatement = PrepStHelper.genDeleteByIdStatement(tableName, idAttr);
		this.connectionPool = ConnectionPoolFactory.getInstance();
	}

	@Override
	public List<Integer> add(List<T> entityList) throws InvalidEntityException, InternalDAOException {
		if (entityList.isEmpty()) {
			return new ArrayList<>();
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet insertedIdsRs = null;
		try {
			try {
				conn = connectionPool.getConnection();
				ps = conn.prepareStatement(insertPrepStatement, PreparedStatement.RETURN_GENERATED_KEYS);
				for (T entity : entityList) {
					if (!validateEntityForInsert(entity)) {
						throw new InvalidEntityException(entity);
					}
					entity.setId(null);
					fillPrepStatementWithResultSet(entity, ps, false);
					log.debug(ps);
					ps.addBatch();
				}
				try {
					ps.executeBatch();
				} catch (SQLException e) {
					switch (e.getErrorCode()) {
					case MySQLErrorCodes.ER_NO_REFERENCED_ROW:
					case MySQLErrorCodes.ER_NO_REFERENCED_ROW_2:
					case MySQLErrorCodes.ER_DUP_ENTRY:
						throw new InvalidEntityException(e);
					}
					log.debug("add error, code: {} / mysql: {}", e.getSQLState(), e.getErrorCode());
					throw new InternalDAOException(e);
				}
				conn.commit();
				List<Integer> insertedIdsList = new ArrayList<>();
				insertedIdsRs = ps.getGeneratedKeys();
				while (insertedIdsRs.next()) {
					insertedIdsList.add(insertedIdsRs.getInt(1));
				}
				return insertedIdsList;
			} catch (SQLException e) {
				if (conn != null) {
					conn.rollback();
				}
				throw e;
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					connectionPool.releaseConnection(conn);
				}
			}
		} catch (SQLException e) {
			throw new InternalDAOException(e);
		}
	}

	@Override
	public List<T> getByFilter(Filter filter) throws InvalidRequestException, InternalDAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> entityList = new ArrayList<>();
		T entity;
		try {
			try {
				conn = connectionPool.getConnection();
				if (!validateFilter(filter, tableAttributes)) {
					throw new InvalidRequestException(
							DBErrorMessages.getFilterDoesntMatchTableMessage(tableName, filter),
							new IllegalArgumentException());
				}
				ps = conn.prepareStatement(PrepStHelper.genSelectByFilterStatement(tableName, filter));
				PrepStHelper.fill(ps, true, filter);
				log.trace(ps);
				try {
					rs = ps.executeQuery();
				} catch (SQLException e) {
					log.debug("select error, code: {} / mysql: {}", e.getSQLState(), e.getErrorCode());
					throw new InvalidRequestException(e);
				}
				while (rs.next()) {
					entity = createEntityByResultSet(rs);
					entityList.add(entity);
				}
				return entityList;
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					connectionPool.releaseConnection(conn);
				}
			}
		} catch (SQLException e) {
			throw new InternalDAOException(e);
		}
	}

	@Override
	public void update(List<T> entityList) throws InvalidEntityException, InternalDAOException {
		if (entityList.isEmpty()) {
			return;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				conn = connectionPool.getConnection();
				boolean[] nullAttributesStates;
				for (T entity : entityList) {
					if (entity.getId() == null) {
						conn.rollback();
						throw new InvalidEntityException(entity,
								DBErrorMessages.genIdIsNotDefinedMessage(entity.getClass().getName()));
					}
					nullAttributesStates = getNullAttributesStates(entity);
					int nullAttrCount = 0;
					for (int i = 0; i < nullAttributesStates.length; i++) {
						if (nullAttributesStates[i]) {
							nullAttrCount++;
						}
					}
					if (nullAttrCount == tableAttributes.length - 1) {
						continue;
					}
					ps = conn.prepareStatement(PrepStHelper.genUpdateByIdStatement(tableName, tableAttributes,
							nullAttributesStates, idAttr));
					fillPrepStatementWithResultSet(entity, ps, true);
					ps.setInt(ps.getParameterMetaData().getParameterCount(), entity.getId());
					log.trace(ps);
					ps.addBatch();
				}
				if (ps != null) {
					try {
						ps.executeBatch();
					} catch (SQLException e) {
						switch (e.getErrorCode()) {
						case MySQLErrorCodes.ER_NO_REFERENCED_ROW:
						case MySQLErrorCodes.ER_NO_REFERENCED_ROW_2:
						case MySQLErrorCodes.ER_DUP_ENTRY:
							throw new InvalidEntityException(e);
						}
						log.debug("update error, code: {} / mysql: {}", e.getSQLState(), e.getErrorCode());
						throw new InternalDAOException(e);
					}
					conn.commit();
				}
			} catch (SQLException e) {
				if (conn != null) {
					conn.rollback();
				}
				throw e;
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					connectionPool.releaseConnection(conn);
				}
			}
		} catch (SQLException e) {
			throw new InternalDAOException(e);
		}

	}

	@Override
	public void deleteByIdsListCascade(List<Integer> entitiesIdsList)
			throws InvalidRequestException, InternalDAOException {
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
						throw new InvalidRequestException(DBErrorMessages.genIdIsNotDefinedMessage(tableName),
								new NullPointerException());
					}
					ps.setInt(1, entitysId);
					log.trace(ps);
					ps.addBatch();
				}
				try {
					ps.executeBatch();
				} catch (SQLException e) {
					switch (e.getErrorCode()) {
						
					}
					log.debug("delete error, code: {} / mysql: {}", e.getSQLState(), e.getErrorCode());
					throw new InternalDAOException(e);
				}
				conn.commit(); // IT WORKS WITHOUT COMMIT!??? how
			} catch (SQLException e) {
				if (conn != null) {
					conn.rollback();
				}
				throw e;
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					connectionPool.releaseConnection(conn);
				}
			}
		} catch (SQLException e) {
			throw new InternalDAOException(e);
		}
	}

	@Override
	public Integer add(T entity) throws NoSuchEntityException, InvalidEntityException, InternalDAOException {
		List<Integer> entitiesList = add(Arrays.asList(entity));
		if (entitiesList.isEmpty()) {
			throw new NoSuchEntityException();
		}
		return entitiesList.get(0);
	}

	@Override
	public T getById(Integer id) throws NoSuchEntityException, InvalidRequestException, InternalDAOException {
		List<T> entityList = getByFilter(new Filter(idAttr.getAttrName(), String.valueOf(id)));
		if (entityList.isEmpty()) {
			throw new NoSuchEntityException();
		}
		return entityList.get(0);
	}

	@Override
	public void update(T entity) throws InvalidEntityException, InternalDAOException {
		update(Arrays.asList(entity));
	}

	@Override
	public void deleteByIdCascade(Integer id) throws InvalidRequestException, InternalDAOException {
		deleteByIdsListCascade(Arrays.asList(id));
	}

	private boolean validateFilter(Filter filter, TableAttr[] allowableAttributes) {
		for (int i = 0; i < filter.size(); i++) {
			for (int j = 0; j < allowableAttributes.length; j++) {
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

	public abstract void fillPrepStatementWithResultSet(T entity, PreparedStatement ps, boolean skipNull)
			throws SQLException;

	public abstract T createEntityByResultSet(ResultSet rs) throws SQLException;

	public abstract boolean[] getNullAttributesStates(T entity);

}
