package by.epam.training.studentcourses.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.dao.EntityDAO;
import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.service.EntityCRUDService;
import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidEntitiesException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.Identifiable;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.entity.User;

public abstract class EntityCRUDAbstractService<T extends Identifiable> implements EntityCRUDService<T> {

	private static Logger log = LogManager.getLogger(EntityCRUDAbstractService.class);
	protected EntityDAO<T> dao;
	protected EntityValidator<T> validator;
	protected CRUDAuthorizator<T> authorizator;
	
	protected void validateForAdd(List<T> entitiesList) throws InvalidEntitiesException {
		List<List<TableAttr>> invalidAttrLists = new ArrayList<>();
		for (int i = 0; i < entitiesList.size(); i++) {
			List<TableAttr> invalidAttrsList = validator.validate(entitiesList.get(i), false);
			invalidAttrLists.add(invalidAttrsList);
		}
		for (List<TableAttr> list : invalidAttrLists) {
			if (!list.isEmpty()) {
				throw new InvalidEntitiesException(invalidAttrLists);
			}
		}
	}
	
	protected void addWithoutValidation(List<T> entitiesList) throws InternalServiceException {
		List<Integer> entitiesIdsList = null;
		try {
			entitiesIdsList = dao.add(entitiesList);
		} catch (by.epam.training.studentcourses.dao.exception.InvalidEntityException | InternalDAOException e) {
			throw new InternalServiceException(e);
		}
		if (entitiesList.size() != entitiesIdsList.size()) {
			throw new InternalServiceException();
		}
		for (int i = 0; i < entitiesList.size(); i++) {
			entitiesList.get(i).setId(entitiesIdsList.get(i));
		}
	}

	@Override
	public void add(User user, List<T> entitiesList)
			throws InternalServiceException, NotAllowedException, InvalidEntitiesException {
		//authorizator.add(user, entitiesList);
		validateForAdd(entitiesList);
		addWithoutValidation(entitiesList);
	}

	@Override
	public List<T> getByFilter(User user, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException {
		//authorizator.getByFilter(user, filter);
		try {
			return dao.getByFilter(filter);
		} catch (by.epam.training.studentcourses.dao.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		}

	}

	@Override
	public void update(User user, List<T> entityList)
			throws InvalidEntitiesException, InternalServiceException, NotAllowedException {
		authorizator.update(user, entityList);
		List<List<TableAttr>> invalidAttrLists = new ArrayList<>();
		for (int i = 0; i < entityList.size(); i++) {
			List<TableAttr> invalidAttrsList = validator.validate(entityList.get(i), true);
			invalidAttrLists.add(invalidAttrsList);
		}
		for (List<TableAttr> list : invalidAttrLists) {
			if (!list.isEmpty()) {
				throw new InvalidEntitiesException(invalidAttrLists);
			}
		}
		try {
			dao.update(entityList);
		} catch (by.epam.training.studentcourses.dao.exception.InvalidEntityException e) {
			throw new InvalidEntitiesException(e);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		}
	}

	@Override
	public void deleteByIdsList(User user, List<Integer> entitiiesIdsList)
			throws InvalidRequestException, NotAllowedException, InternalServiceException {
		authorizator.deleteByIdsList(user, entitiiesIdsList);
		try {
			dao.deleteByIdsListCascade(entitiiesIdsList);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		} catch (by.epam.training.studentcourses.dao.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		}
	}

	@Override
	public void add(User user, T entity)
			throws InternalServiceException, NotAllowedException, InvalidEntitiesException {
		add(user, Arrays.asList(entity));
	}

	@Override
	public T getById(User user, Integer id)
			throws InvalidRequestException, InternalServiceException, NoSuchEntityException, NotAllowedException {
		authorizator.getById(user, id);
		try {
			return dao.getById(id);
		} catch (by.epam.training.studentcourses.dao.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		} catch (InternalDAOException e) {
			throw new InternalServiceException(e);
		} catch (by.epam.training.studentcourses.dao.exception.NoSuchEntityException e) {
			throw new NoSuchEntityException(e);
		}
	}

	@Override
	public void update(User user, T entity)
			throws InvalidEntitiesException, InternalServiceException, NotAllowedException {
		update(user, Arrays.asList(entity));
	}

	@Override
	public void deleteById(User user, Integer id)
			throws InvalidRequestException, NotAllowedException, InternalServiceException {
		deleteByIdsList(user, Arrays.asList(id));
	}

	protected void init(EntityDAO<T> entityDAO, EntityValidator<T> validator, CRUDAuthorizator<T> authorizator) {
		this.dao = entityDAO;
		this.validator = validator;
		this.authorizator = authorizator;
	}

}
