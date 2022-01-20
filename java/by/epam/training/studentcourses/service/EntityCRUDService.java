package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;

public interface EntityCRUDService<T> {
	
	void add(List<T> usersList) throws ServiceException;
	List<T> getByFilter(Filter filter) throws ServiceException;
	void update(List<T> usersList) throws ServiceException;
	void deleteCascade(List<T> usersList) throws ServiceException;
}
