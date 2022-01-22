package by.epam.training.studentcourses.service.impl;

import java.util.Arrays;
import java.util.List;

import by.epam.training.studentcourses.dao.EntityDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.service.EntityCRUDService;
import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.service.exception.InvalidEntityException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.Identifiable;
import by.epam.training.studentcourses.util.TableAttr;

public abstract class EntityCRUDAbstractService<T extends Identifiable> implements EntityCRUDService<T> {
	
	private EntityDAO<T> dao;
	private EntityValidator<T> validator;

	@Override
	public void add(List<T> entityList) throws ServiceException {
		TableAttr invalidAttr;
		for (int i = 0; i < entityList.size(); i ++) {
			invalidAttr = validator.validate(entityList.get(i));
			if (invalidAttr != null) {
				throw new InvalidEntityException(entityList.get(i));
			}
		}
		try {
			dao.add(entityList);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<T> getByFilter(Filter filter) throws ServiceException {
		try {
			return dao.getByFilter(filter);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(List<T> entityList) throws ServiceException {
		try {
			dao.update(entityList);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdsList(List<Integer> entityList) throws ServiceException {
		try {
			dao.deleteByIdsListCascade(entityList);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void add(T entity) throws ServiceException {
		try {
			dao.add(entity);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override 
	public T getById(Integer id) throws ServiceException {
		try {
			return dao.getById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void update(T entity) throws ServiceException {
		try {
			dao.update(Arrays.asList(entity));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override 
	public void deleteById(Integer id) throws ServiceException {
		try {
			dao.deleteById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	protected void init(EntityDAO<T> entityDAO, EntityValidator<T> validator) {
		this.dao = entityDAO;
		this.validator = validator;
	}
	
}
