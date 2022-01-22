package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;

public interface EntityCRUDService<T> {
	void add(List<T> usersList) throws ServiceException;
	void add(T entity) throws ServiceException;
	List<T> getByFilter(Filter filter) throws ServiceException;
	T getById(Integer id) throws ServiceException;;
	void update(List<T> usersList) throws ServiceException;
	void update(T entity) throws ServiceException;;
	void deleteByIdsList(List<Integer> entitiesList) throws ServiceException;
	void deleteById(Integer id) throws ServiceException;
	
}
