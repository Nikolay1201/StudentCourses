package by.epam.training.studentcourses.dao;

import by.epam.training.studentcourses.dao.impl.CourseDAOImpl;
import by.epam.training.studentcourses.dao.impl.CoursePlanDAOImpl;
import by.epam.training.studentcourses.dao.impl.LessonDAOImpl;
import by.epam.training.studentcourses.dao.impl.StudentsHaveCoursesPlansDAOImpl;
import by.epam.training.studentcourses.dao.impl.UserDAOImpl;
import by.epam.training.studentcourses.dao.impl.UserSessionTokenDAOImpl;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;

public class EntityDAOFactory {
	private static UserDAO userDAOInstance = new UserDAOImpl();
	private static CourseDAO courseDAOInstance = new CourseDAOImpl();
	private static CoursePlanDAO coursePlanDAOInstance = new CoursePlanDAOImpl();
	private static LessonDAO lessonDAOInstance = new LessonDAOImpl();
	private static UserSessionTokenDAO userSessionTokenDAOInstance = new UserSessionTokenDAOImpl();
	private static StudentsHaveCoursesPlansDAO studentsHaveCoursesPlansDAOInstance = new StudentsHaveCoursesPlansDAOImpl();

	public static UserDAO getUserDAO() {
		return userDAOInstance;
	}

	public static CourseDAO getCourseDAO() {
		return courseDAOInstance;
	}

	public static CoursePlanDAO getCoursePlanDAO() {
		return coursePlanDAOInstance;
	}

	public static LessonDAO getLessonDAO() {
		return lessonDAOInstance;
	}

	public static UserSessionTokenDAO getUserSessionTokenDAO() {
		return userSessionTokenDAOInstance;
	}
	
	public static StudentsHaveCoursesPlansDAO getStudentsHaveCoursesPlansDAO() {
		return studentsHaveCoursesPlansDAOInstance;
	}

	private EntityDAOFactory() {
	}
}
