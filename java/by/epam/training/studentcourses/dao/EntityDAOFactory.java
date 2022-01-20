package by.epam.training.studentcourses.dao;

import by.epam.training.studentcourses.dao.impl.CourseDAOImpl;
import by.epam.training.studentcourses.dao.impl.CoursePlanDAOImpl;
import by.epam.training.studentcourses.dao.impl.LessonDAOImpl;
import by.epam.training.studentcourses.dao.impl.UserDAOImpl;

public class EntityDAOFactory {
	private static UserDAO userDAOInstance = new UserDAOImpl();
	private static CourseDAO courseDAOInstance = new CourseDAOImpl();	
	private static CoursePlanDAO coursePlanInstance = new CoursePlanDAOImpl();	
	private static LessonDAO lessonDAOInstance = new LessonDAOImpl();
	
	public static UserDAO getUserDAO() {
		return userDAOInstance;
	}

	public static CourseDAO getCourseDAO() {
		return courseDAOInstance;
	}

	public static CoursePlanDAO getCoursePlanDAO() {
		return coursePlanInstance;
	}

	public static LessonDAO getLessonDAO() {
		return lessonDAOInstance;
	}
	
	private EntityDAOFactory() {}
}
