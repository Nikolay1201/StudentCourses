package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAO;
import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.service.LessonService;
import by.epam.training.studentcourses.service.Service;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.ServiceException;

public class ServiceImpl implements Service {
	
	private DAO dao = DAOFactory.getInstance();

	
	@Override
	public void init() throws ServiceException {
		try {
			dao.init();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}
	
	@Override	
	public void close() throws ServiceException {
		try {
			dao.close();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public UserService getUserService() {
		return null;
	}

	@Override
	public CourseService getCourseService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoursePlanService getCourseplanService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LessonService getLessonService() {
		// TODO Auto-generated method stub
		return null;
	}
}
