package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAO;
import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.service.exception.ServiceException;

public class ServiceImpl {
	
	private DAO dao = DAOFactory.getInstance();
	
	public void init() throws ServiceException {
		try {
			dao.init();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}
	
	public void close() throws ServiceException {
		try {
			dao.close();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}
}
