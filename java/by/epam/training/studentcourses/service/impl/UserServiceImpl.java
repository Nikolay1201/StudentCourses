package by.epam.training.studentcourses.service.impl;

import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.Hashing;
import by.epam.training.studentcourses.service.HashingFactory;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.InvalidEntityException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.FiltrationType;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;

public class UserServiceImpl extends EntityCRUDAbstractService<User> implements UserService {
	
	private Hashing hashing = HashingFactory.getInstance();
	
	public UserServiceImpl() {
		super(DAOFactory.getInstance().getUserDAO(), ValidatorFactory.getUserValidator());
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
	public void add(List<User> entityList) throws ServiceException {
		for (User user : entityList) {
			user.setPassword(hashing.hashString(user.getPassword()));
		}
		super.add(entityList);
	}
	
	@Override
	public User authorize(String login, String password) throws ServiceException {
		Filter filter = new Filter();
		filter.addCondition(FiltrationType.EQUALS, Tables.Users.Attr.LOGIN.getAttrName(), login);
		password = hashing.hashString(password);
		filter.addCondition(FiltrationType.EQUALS, Tables.Users.Attr.PASSWORD.getAttrName(), password);
		List<User> usersList = getByFilter(filter);
		if (usersList.size() != 1) {
			throw new InvalidEntityException();
		}
		//to be continued
		return null;
	}

	@Override
	public void logout(String logout) {
		// stub		
	}
	
}
