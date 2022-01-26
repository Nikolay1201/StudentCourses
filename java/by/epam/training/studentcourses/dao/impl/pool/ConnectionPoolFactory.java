package by.epam.training.studentcourses.dao.impl.pool;

import by.epam.training.studentcourses.util.constant.MySQLDBParams;

public class ConnectionPoolFactory {
	
	private static final ConnectionPool instance = new ConnectionPoolImpl();
	
	static {
		instance.setParams(
			MySQLDBParams.DB_URL, 
			MySQLDBParams.ADMIN_LOGIN, 
			MySQLDBParams.ADMIN_PASSWORD,
			MySQLDBParams.INITIAL_POOL_SIZE);
	}
	
	private ConnectionPoolFactory() {}
	
	public static ConnectionPool getInstance() {
		return instance;
	}
			
}
