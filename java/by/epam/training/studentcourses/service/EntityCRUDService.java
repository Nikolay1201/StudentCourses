package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.User;

public interface EntityCRUDService<T> {
	
	void add(User user, List<T> entitiesList) throws ServiceException;
	void add(User user, T entity) throws ServiceException;
	List<T> getByFilter(User user, Filter filter) throws ServiceException;
	T getById(User user, Integer id) throws ServiceException;
	void update(User user, List<T> entitiesList) throws ServiceException;
	void update(User user, T entity) throws ServiceException;
	void deleteByIdsList(User user, List<Integer> entitiesList) throws ServiceException;
	void deleteById(User user, Integer id) throws ServiceException;
	
}
