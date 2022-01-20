package by.epam.training.studentcourses.service;

import by.epam.training.studentcourses.service.exception.ServiceException;

public interface Service {
	
	public void init() throws ServiceException;
	public void close() throws ServiceException;
}
