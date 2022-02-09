package by.epam.training.studentcourses.dao;

import by.epam.training.studentcourses.dao.exception.InternalDAOException;

public interface DAO {

	public void init() throws InternalDAOException;
	public void close() throws InternalDAOException;
	public UserDAO getUserDAO();
	public CourseDAO getCourseDAO();
	public CoursePlanDAO getCoursePlanDAO();
	public LessonDAO getLessonDAO();
	public UserSessionTokenDAO getUserSessionTokenDAO();
	public StudentsHaveCoursesPlansDAO getStudentsHaveCoursesPlansDAO();
	
}
	