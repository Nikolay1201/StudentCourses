package by.epam.training.studentcourses.dao;

import by.epam.training.studentcourses.dao.exception.DAOException;

public interface DAO {

	public void init() throws DAOException;
	public void close() throws DAOException;
	public UserDAO getUserDAO();
	public CourseDAO getCourseDAO();
	public CoursePlanDAO getCoursePlanDAO();
	public LessonDAO getLessonDAO();
	public UserSessionTokenDAO getUserSessionTokenDAO();
	
}
	