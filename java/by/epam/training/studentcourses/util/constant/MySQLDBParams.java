package by.epam.training.studentcourses.util.constant;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

public class MySQLDBParams {
	public static final String DB_URL = "jdbc:mysql://localhost/task4";
	public static final String ADMIN_LOGIN = "root";
	public static final String ADMIN_PASSWORD = "qwerqwer";
	public static final int INITIAL_POOL_SIZE = 10;
	public static final String SALT = "some-random-string-228-337";
	
	private MySQLDBParams() {}
}
