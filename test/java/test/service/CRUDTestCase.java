package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.training.studentcourses.dao.impl.pool.ConnectionPool;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPoolFactory;
import by.epam.training.studentcourses.service.Service;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.impl.UserServiceImpl;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;
public class CRUDTestCase {
	
	private static ConnectionPool pool = ConnectionPoolFactory.getInstance();
	private User testUser = new User(
		null,
		UserRole.ADMIN,
		"Nikolay2", 
		"Shilov", 
		"Alexandrovich",
		LocalDate.parse("2001-12-12"),
		"+375 (25) 727-02-07",
		"nikolashkashilov@gmail.com",
		"Nikolay228337",
		"qwerty1",
		"a very lazy man",
		LocalDateTime.now()
		);
	private String uniqueString;
	private Service service = ServiceFactory.getInstance();
	private UserService userSerivice = new UserServiceImpl();
	
	@BeforeClass
	public static void init() throws SQLException {
		pool.init();
	}
	
	@AfterClass
	public static void close() throws SQLException {
		pool.close();
	}
	
	@Test 
	public void addUser() {
		
	}
	
}
