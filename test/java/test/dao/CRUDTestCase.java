package dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.training.studentcourses.dao.CourseDAO;
import by.epam.training.studentcourses.dao.DAO;
import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPool;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPoolFactory;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;

public class CRUDTestCase {
	
	private static ConnectionPool pool = ConnectionPoolFactory.getInstance();
	private Course testCourse;
	private String uniqueString;
	private DAO dao = DAOFactory.getInstance();
	private UserDAO userDAO = dao.getUserDAO();
	private CourseDAO courseDAO = dao.getCourseDAO();
	
	@BeforeClass
	public static void init() throws SQLException {
		pool.init();
		Configurator.setRootLevel(Level.TRACE);
	}
	
	@AfterClass
	public static void close() throws SQLException {
		pool.close();
	}
	
	@Before
	public void genUniqueCourse() {
		uniqueString = LocalDateTime.now().toString();
		testCourse = new Course(
			228337,
			uniqueString,
			"13 month", 
			"descr",
			LocalDateTime.now()
			);
	}
	
	@Test 
	public void addCourse() throws DAOException {
		Integer id = courseDAO.add(testCourse);
		List<Course> coursesList = courseDAO.getByFilter(new Filter(
				Tables.Courses.Attr.COURSE_ID, id.toString()));
		assertEquals(1, coursesList.size());
		courseDAO.deleteByIdCascade(id);
	}
	
	@Test
	public void selectCouse() throws DAOException {
		Integer id = courseDAO.add(testCourse);
		List<Course> coursesList = 
				courseDAO.getByFilter(new Filter(Tables.Courses.Attr.NAME, testCourse.getName()));
		assertEquals(1, coursesList.size());
		courseDAO.deleteByIdCascade(id);
	}
	
	@Test
	public void updateCouse() throws DAOException {
		Integer id = courseDAO.add(testCourse);
		uniqueString += "q";
		testCourse.setName(uniqueString);
		testCourse.setId(id);
		courseDAO.update(testCourse);
		List<Course> courseList = 
				courseDAO.getByFilter(new Filter(Tables.Courses.Attr.COURSE_ID, id.toString()));
		assertEquals(1, courseList.size());
		assertEquals(uniqueString, courseList.get(0).getName());
		courseDAO.deleteByIdCascade(id);
	}
	
	public void deleteCouseTest() throws DAOException {
		Integer id = courseDAO.add(testCourse);
		courseDAO.deleteByIdCascade(id);
		Assert.assertThrows(NoSuchElementException.class, () -> {
			courseDAO.getById(id);
		});
	}
	
	/*
	public void addUserTest() throws DAOException {
		List<User> addUsersList = new ArrayList<User>();
		User user = new User(
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
		addUsersList.add(user);
		ud.add(addUsersList);
	}
	
	public void selectUserTest() throws DAOException {
		Filter filter = new Filter();
		filter.addCondition(FiltrationType.LIKE, Tables.Users.Attr.NAME.getAttrName(), "%Nik%");
		List<User> getUsersList = ud.getByFilter(filter);
		for (User u : getUsersList) {
			System.out.println(u.toString());
		}
	}
	
	public void updateUserTest() throws DAOException {
		List<User> usersList = new ArrayList<User>();
		User user = new User(
				6,
				UserRole.ADMIN,
				"1", 
				"1", 
				"1",
				LocalDate.parse("2001-12-12"),
				"1",
				"1@gmail.com",
				"1",
				"1",
				"1",
				LocalDateTime.now()
				);
		usersList.add(user);
		ud.update(usersList);
	}
	
	public void deleteUserTest() throws DAOException {
		User user = new User(
				6,
				UserRole.ADMIN,
				"Nikolay", 
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
		List<User> usersIdList = new ArrayList<User>();
		usersIdList.add(user);
		ud.deleteByIdsListCascade(usersIdList);
	}
	
	*/

}
