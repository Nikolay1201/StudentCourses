package by.epam.training.studentcourses.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.dao.EntityDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.dao.exception.InvalidEntityException;
import by.epam.training.studentcourses.dao.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.EntityCRUDService;
import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.Identifiable;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.entity.User;

public abstract class EntityCRUDAbstractService<T extends Identifiable> 
	implements EntityCRUDService<T> {
	
	private static Logger log = LogManager.getLogger(EntityCRUDAbstractService.class);
	protected EntityDAO<T> dao;
	protected EntityValidator<T> validator;
	protected CRUDAuthorizator<T> authorizator;

	@Override
	public void add(User user, List<T> entityList) throws ServiceException {
		authorizator.add(user, entityList);
		TableAttr invalidAttr;
		for (int i = 0; i < entityList.size(); i ++) {
			invalidAttr = validator.validate(entityList.get(i));
			
			if (invalidAttr != null) {
				//log.debug("invalid attr: {}", invalidAttr.getAttrName());
				//throw new InvalidEntityException(entityList.get(i));
			}
		}
		List<Integer> entitiesIdsList = null;
		try {
			entitiesIdsList = dao.add(entityList);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		} catch (InvalidEntityException e) {
			throw new by.epam.training.studentcourses.service.exception.InvalidEntityException(e);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		if (entityList.size() != entitiesIdsList.size()) {
			throw new InternalServiceException();
		}
		for (int i = 0; i < entityList.size(); i ++) {
			entityList.get(i).setId(entitiesIdsList.get(i));
		}
	}

	@Override
	public List<T> getByFilter(User user, Filter filter) throws ServiceException {
		authorizator.getByFilter(user, filter);
		try {
			return dao.getByFilter(filter);
		} catch (InvalidRequestException e) {
			throw new by.epam.training.studentcourses.service.exception.InvalidRequestException(e);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(User user, List<T> entityList) throws ServiceException {
		authorizator.update(user, entityList);
		try {
			dao.update(entityList);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		} catch (InvalidEntityException e) {
			throw new by.epam.training.studentcourses.service.exception.InvalidEntityException(e);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdsList(User user, List<Integer> entitiiesIdsList) throws ServiceException {
		authorizator.deleteByIdsList(user, entitiiesIdsList);

		try {
			dao.deleteByIdsListCascade(entitiiesIdsList);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		} catch (InvalidEntityException e) {
			throw new by.epam.training.studentcourses.service.exception.InvalidEntityException(e);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void add(User user, T entity) throws ServiceException {
		add(user, Arrays.asList(entity));
	}
	
	@Override 
	public T getById(User user, Integer id) throws ServiceException {
		authorizator.getById(user, id);
		try {
			return dao.getById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void update(User user, T entity) throws ServiceException {
		update(user, Arrays.asList(entity));
	}
	
	@Override 
	public void deleteById(User user, Integer id) throws ServiceException {
		deleteByIdsList(user, Arrays.asList(id));
	}

	protected void init(EntityDAO<T> entityDAO, EntityValidator<T> validator, CRUDAuthorizator<T> authorizator) {
		this.dao = entityDAO;
		this.validator = validator;
		this.authorizator = authorizator;
	}
	
}
