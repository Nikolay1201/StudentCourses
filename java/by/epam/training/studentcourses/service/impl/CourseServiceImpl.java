package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.util.entity.Course;

public class CourseServiceImpl extends EntityCRUDAbstractService<Course> implements CourseService {
	
	public CourseServiceImpl() {
		init(DAOFactory.getInstance().getCourseDAO(), ValidatorFactory.getCourseValidator());
	}
	
}

