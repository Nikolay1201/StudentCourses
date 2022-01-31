package by.epam.training.studentcourses.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class CourseServiceImpl extends EntityCRUDAbstractService<Course> implements CourseService {
	
	public CourseServiceImpl() {
		init(DAOFactory.getInstance().getCourseDAO(), ValidatorFactory.getCourseValidator(),
				new CRUDAuthorizator<>(UserRole.ADMIN, UserRole.TRAINER) {
			@Override
			public void getByFilter(User user, Filter filter) {
				return;
			}
		});
	}
	
	@Override 
	public void add(User user, List<Course> coursesList) throws ServiceException {
		for (Course c : coursesList) {
			c.setCreationTime(LocalDateTime.now());
		}
		super.add(user, coursesList);
	}

	@Override
	public List<Course> getCoursesOfUser(User user, User userWithCoursesRequired) {
		return null;
	}
	
}

