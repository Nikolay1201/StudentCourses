package by.epam.training.studentcourses.service;

import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.service.impl.UseSessionInfo;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserSessionToken;

public interface UserService extends EntityCRUDService<User> {
	
	public UseSessionInfo authorize(String login, String password) throws ServiceException;
	public UseSessionInfo confirmAuthenticationBySessionToken(String sessionToken) throws ServiceException;
	void logout(UserSessionToken userSessionToken) throws ServiceException;
	
}
