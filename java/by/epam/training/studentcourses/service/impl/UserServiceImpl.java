package by.epam.training.studentcourses.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.UserSessionTokenDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidEntitiesException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.service.exception.WrongPasswordException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.UseSessionInfo;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;
import by.epam.training.studentcourses.util.entity.SessionToken;

public class UserServiceImpl extends EntityCRUDAbstractService<User> implements UserService {

	private UserSessionTokenDAO userSessionTokenDAO = DAOFactory.getInstance().getUserSessionTokenDAO();
	private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
	private Hashing hashing = HashingFactory.getInstance();

	{
		init(userDAO, ValidatorFactory.getUserValidator(), new CRUDAuthorizator<User>(UserRole.ADMIN) {

			@Override
			public void add(User user, List<User> usersList) throws NotAllowedException {
				if (user.getRole() != UserRole.ADMIN) {
					throw new NotAllowedException(UserRole.SYSTEM);
				}
			}

			@Override
			public void update(User user, User userToUpdate) throws NotAllowedException {
				if (!user.getId().equals(userToUpdate.getId())) {
					super.update(user, userToUpdate);
				} else {
					throw new NotAllowedException(anythingAllowedRolesList);
				}
			}

			@Override
			public void deleteById(User user, Integer id) throws NotAllowedException {
				if (!user.getId().equals(id)) {
					super.deleteById(user, id);
				} else {
					throw new NotAllowedException(anythingAllowedRolesList); // USE!
				}
			}

			@Override
			public void getById(User user, Integer id) throws NotAllowedException {
				if (!user.getId().equals(id)) {
					super.getById(user, id);
				} else {
					throw new NotAllowedException(anythingAllowedRolesList);
				}
			}
			
			@Override
			public void getByFilter(User user, Filter filter) {
				return;
			}
			
		});
	}

	@Override
	public void add(User user, List<User> usersList)
			throws InternalServiceException, NotAllowedException, InvalidEntitiesException {
		authorizator.add(user, usersList);
		validateForAdd(usersList);
		for (int i = 0; i < usersList.size(); i++) {
			usersList.get(i).setRegistrationDateTime(LocalDateTime.now());
			usersList.get(i).setPassword(hashing.hashString(usersList.get(i).getPassword()));
		}
		addWithoutValidation(usersList);
	}

	@Override
	public void add(User user, User userToAdd)
			throws InternalServiceException, NotAllowedException, InvalidEntitiesException {
		add(user, Arrays.asList(userToAdd));
	}

	@Override
	public void update(User user, List<User> usersList)
			throws InvalidEntitiesException, InternalServiceException, NotAllowedException {
		for (User u : usersList) {
			u.setPassword(null);
		}
		super.update(user, usersList);
	}

	@Override
	public List<User> getByFilter(User user, Filter filter)
			throws InvalidRequestException, InternalServiceException, NotAllowedException {
		List<User> usersList = super.getByFilter(user, filter);
		for (User selectedUser : usersList) {
			selectedUser.setPassword(null);
		}
		return usersList;
	}

	@Override
	public UseSessionInfo authorize(String login, String password) throws ServiceException {
		List<User> usersList = null;
		try {
			usersList = userDAO.getByFilter(new Filter(Tables.Users.Attr.LOGIN, login));
			if (usersList.isEmpty()) {
				throw new NoSuchEntityException();
			}
			User user = usersList.get(0);
			if (!user.getPassword().equals(hashing.hashString(password))) {
				throw new WrongPasswordException();
			}
			user.setPassword(null);
			SessionToken userSessionToken = new SessionToken(user.getId(), genSessionToken(user.getLogin()));
			userSessionTokenDAO.add(userSessionToken);
			return new UseSessionInfo(user, userSessionToken);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public UseSessionInfo confirmAuthenticationBySessionToken(String sessionToken) throws ServiceException {
		try {
			List<SessionToken> userSessionTokensList = userSessionTokenDAO
					.getByFilter(new Filter(Tables.UserSessionToken.Attr.SESSION_TOKEN.getAttrName(), sessionToken));
			if (userSessionTokensList.isEmpty()) {
				throw new NoSuchEntityException();
			}
			SessionToken userSessionToken = userSessionTokensList.get(0);
			return new UseSessionInfo(userDAO.getById(userSessionToken.getId()), userSessionToken);
		} catch (by.epam.training.studentcourses.dao.exception.NoSuchEntityException e) {
			throw new NoSuchEntityException(e);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void logout(SessionToken userSessionToken) throws ServiceException {
		try {
			userSessionTokenDAO.deleteByIdCascade(userSessionToken.getId());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private String genSessionToken(String baseStr) {
		return hashing.hashString(LocalDateTime.now().toString() + baseStr);
	}

}
