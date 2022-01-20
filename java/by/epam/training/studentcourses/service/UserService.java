package by.epam.training.studentcourses.service;

import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.entity.User;

public interface UserService extends EntityCRUDService<User> {
	
	public User authorize(String login, String password) throws ServiceException;
	public void logout(String logout);
	
}
