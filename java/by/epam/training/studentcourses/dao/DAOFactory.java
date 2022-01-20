package by.epam.training.studentcourses.dao;

import by.epam.training.studentcourses.dao.impl.DAOImpl;

public class DAOFactory {
	private static DAO dao = new DAOImpl();
	
	public static DAO getInstance() {
		return dao;
	}
	
	private DAOFactory() {}	
}
