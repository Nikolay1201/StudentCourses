package by.epam.training.studentcourses.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.UserSessionTokenDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.service.exception.WrongPasswordException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserSessionToken;

public class UserServiceImpl extends EntityCRUDAbstractService<User> implements UserService {
	
	private UserSessionTokenDAO userSessionTokenDAO = 
			DAOFactory.getInstance().getUserSessionTokenDAO();
	private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
	private Hashing hashing = HashingFactory.getInstance();
	
	public UserServiceImpl() {
		init(userDAO, ValidatorFactory.getUserValidator());
	}
	
	@Override 
	public List<User> getByFilter(Filter filter) throws ServiceException {
		List<User> usersList = super.getByFilter(filter);
		for (User user : usersList) {
			user.setPassword(null);
		}
		return usersList;
	}
	
	@Override 
	public User getById(Integer id) throws ServiceException {
		User user = super.getById(id);
		user.setPassword(null);
		return user;
	}
	
	@Override 
	public void add(List<User> entityList) throws ServiceException {
		for (User user : entityList) {
			user.setPassword(hashing.hashString(user.getPassword()));
		}
		super.add(entityList);
	}
	
	@Override
	public UseSessionInfo authorize(String login, String password) throws ServiceException {
		List<User> usersList = null;
		try {
			usersList = userDAO.getByFilter(new Filter(Tables.Users.Attr.LOGIN.getAttrName(), login));
			if (usersList.isEmpty()) {
				throw new NoSuchEntityException();
			}
			User user = usersList.get(0);
			if (!user.getPassword().equals(hashing.hashString(password))) {
				throw new WrongPasswordException();
			}
			user.setPassword(null);
			UserSessionToken userSessionToken = 
					new UserSessionToken(user.getId(), genSessionToken(user.getLogin()));
			userSessionTokenDAO.add(userSessionToken);
			return new UseSessionInfo(user, new UserSessionToken(user.getId(), genSessionToken(user.getLogin())));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public User confirmAuthorizationBySessionToken(String sessionToken) throws ServiceException {
		try {
			List<UserSessionToken> userSessionTokensList = userSessionTokenDAO.getByFilter(
					new Filter(Tables.UserSessionToken.Attr.SESSION_TOKEN.getAttrName(), sessionToken));
			if (userSessionTokensList.isEmpty()) {
				throw new NoSuchEntityException();
			}
			UserSessionToken userSessionToken = userSessionTokensList.get(0);
			return userDAO.getById(userSessionToken.getId());
		} catch (by.epam.training.studentcourses.dao.exception.NoSuchEntityException e) {
			throw new NoSuchEntityException(e);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void logout(UserSessionToken userSessionToken) throws ServiceException {
		try {
			userSessionTokenDAO.deleteById(userSessionToken.getId());
		} catch (by.epam.training.studentcourses.dao.exception.NoSuchEntityException e) {
			throw new NoSuchEntityException(e);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private String genSessionToken(String baseStr) {
		return hashing.hashString(LocalDateTime.now().toString() + baseStr);
	}


	
}
