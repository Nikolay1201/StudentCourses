package by.epam.training.studentcourses.dao.impl;

import java.sql.SQLException;

import by.epam.training.studentcourses.dao.CourseDAO;
import by.epam.training.studentcourses.dao.CoursePlanDAO;
import by.epam.training.studentcourses.dao.DAO;
import by.epam.training.studentcourses.dao.EntityDAOFactory;
import by.epam.training.studentcourses.dao.LessonDAO;
import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPoolFactory;

public class DAOImpl implements DAO {
	private static final UserDAO userDAO = EntityDAOFactory.getUserDAO();
	private static final CourseDAO courseDAO = EntityDAOFactory.getCourseDAO();
	private static final CoursePlanDAO coursePlanDAO = EntityDAOFactory.getCoursePlanDAO();
	private static final LessonDAO lessonDAO = EntityDAOFactory.getLessonDAO();
	
	@Override
	public void init() throws DAOException {
		try {
			ConnectionPoolFactory.getInstance().init();
		} catch (SQLException e) {
			//LOGGER
			throw new InternalDAOException();
		}
	}
	
	@Override
	public void close() throws DAOException {
		try {
			ConnectionPoolFactory.getInstance().close();
		} catch (SQLException e) {
			//LOGGER
			throw new InternalDAOException();
		}
	}

	@Override
	public UserDAO getUserDAO() {
		return userDAO;
	}

	@Override
	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	@Override
	public CoursePlanDAO getCourseplanDAO() {
		return coursePlanDAO;
	}
	
	@Override
	public LessonDAO getLessonDAO() {
		return lessonDAO;
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
