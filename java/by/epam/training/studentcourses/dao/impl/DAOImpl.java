package by.epam.training.studentcourses.dao.impl;

import java.sql.SQLException;

import by.epam.training.studentcourses.dao.CourseDAO;
import by.epam.training.studentcourses.dao.CoursePlanDAO;
import by.epam.training.studentcourses.dao.DAO;
import by.epam.training.studentcourses.dao.EntityDAOFactory;
import by.epam.training.studentcourses.dao.LessonDAO;
import by.epam.training.studentcourses.dao.StudentsHaveCoursesPlansDAO;
import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.UserSessionTokenDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPool;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPoolFactory;

public class DAOImpl implements DAO {
	private static UserDAO userDAOInstance = EntityDAOFactory.getUserDAO();
	private static CourseDAO courseDAOInstance = EntityDAOFactory.getCourseDAO();
	private static CoursePlanDAO coursePlanDAOInstance = EntityDAOFactory.getCoursePlanDAO();
	private static LessonDAO lessonDAOInstance = EntityDAOFactory.getLessonDAO();
	private static UserSessionTokenDAO userSessionTokenDAOInstance = EntityDAOFactory.getUserSessionTokenDAO();
	private static StudentsHaveCoursesPlansDAO studentsHaveCoursesPlansDAOInstance = EntityDAOFactory
			.getStudentsHaveCoursesPlansDAO();
	private final ConnectionPool connectionPool = ConnectionPoolFactory.getInstance();

	@Override
	public void init() throws InternalDAOException {
		try {
			connectionPool.init();
		} catch (SQLException e) {
			throw new InternalDAOException(e);
		}
	}

	@Override
	public void close() throws InternalDAOException {
		try {
			connectionPool.close();
		} catch (SQLException e) {
			throw new InternalDAOException(e);
		}
	}

	@Override
	public UserDAO getUserDAO() {
		return userDAOInstance;
	}

	@Override
	public CourseDAO getCourseDAO() {
		return courseDAOInstance;
	}

	@Override
	public CoursePlanDAO getCoursePlanDAO() {
		return coursePlanDAOInstance;
	}

	@Override
	public LessonDAO getLessonDAO() {
		return lessonDAOInstance;
	}

	@Override
	public UserSessionTokenDAO getUserSessionTokenDAO() {
		return userSessionTokenDAOInstance;
	}

	@Override
	public StudentsHaveCoursesPlansDAO getStudentsHaveCoursesPlansDAO() {
		return studentsHaveCoursesPlansDAOInstance;
	}

//	public boolean validateEnteretedTableMetaData() {
//		//check if there is the only primary key attr in the tables
//		//validation! 
//		TableInfo tableInfo = DBMetaInfo.tablesInfoMap.get(tableName);
//		if (tableInfo == null) {
//			throw new InternalDAOException(DBErrorMessages.getTableNotExistsMessage(tableName));
//		}
//		if (tableInfo.getAttrInfoList().size() != tableAttributes.length) {
//			throw new InternalDAOException();
//		}
//		for (int i = 0 ; i < tableAttributes.length; i ++) {
//			if (tableInfo.getAttrInfoByName(tableAttributes[i].getAttrName()) == null) {
//				throw new InternalDAOException(
//						DBErrorMessages.getInvalidTableAttributeMessage(
//								tableName, tableAttributes[i].getAttrName()));
//			}
//		}
//	}

}
