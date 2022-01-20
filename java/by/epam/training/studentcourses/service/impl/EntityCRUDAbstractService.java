package by.epam.training.studentcourses.service.impl;

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

abstract public class EntityCRUDAbstractService<T extends Identifiable> implements EntityCRUDService<T> {
	
	public EntityDAO<T> dao;
	public EntityValidator<T> validator;
	
	public EntityCRUDAbstractService(EntityDAO<T> entityDAO, EntityValidator<T> validator) {
		this.dao = entityDAO;
		this.validator = validator;
	}

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
	public void deleteCascade(List<T> entityList) throws ServiceException {
		try {
			dao.deleteCascade(entityList);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
