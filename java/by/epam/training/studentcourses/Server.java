package by.epam.training.studentcourses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.epam.training.studentcourses.dao.CourseDAO;
import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.FiltrationType;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class Server {

	private static UserDAO ud = DAOFactory.getInstance().getUserDAO();
	private static CourseDAO cd = DAOFactory.getInstance().getCourseDAO();

	//EXAMPLE
	public static void main(String[] args) throws DAOException {
		DAOFactory.getInstance().init();
		updateCouseTest();
		DAOFactory.getInstance().close();
	}
	
	public static void addCourseTest() throws DAOException {
		cd.add(Arrays.asList(new Course(
					12333333,
					"fff",
					"13 month", 
					"",
					LocalDateTime.now()		
					)
				)
		);
	}
	
	public static void selectCouseTest() throws DAOException {
		Filter filter = new Filter();
		filter.addCondition(FiltrationType.LIKE, Tables.Courses.Attr.NAME.getAttrName(), "%he%");
		List<Course> coursesList = cd.getByFilter(filter);
		for (Course u : coursesList) {
			System.out.println(u.toString());
		}
	}
	
	public static void updateCouseTest() throws DAOException {
		Course course = new Course(
			1,
			null,
			null,
			null,
			null
		);
		cd.update(Arrays.asList(course));
	}
	
	public static void deleteCouseTest() throws DAOException {

	}
	
	public static void addUserTest() throws DAOException {
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
	
	public static void selectUserTest() throws DAOException {
		Filter filter = new Filter();
		filter.addCondition(FiltrationType.LIKE, Tables.Users.Attr.NAME.getAttrName(), "%Nik%");
		List<User> getUsersList = ud.getByFilter(filter);
		for (User u : getUsersList) {
			System.out.println(u.toString());
		}
	}
	
	public static void updateUserTest() throws DAOException {
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
	
	public static void deleteUserTest() throws DAOException {
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
		ud.deleteByIdCascade(usersIdList.get(0).getId());
	}

}
