package by.epam.training.studentcourses.controller.impl.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.exception.NotAuthorizedException;
import by.epam.training.studentcourses.controller.impl.dynamic.EntityParserImpl;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class GotoUsersPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoUsersPageCommand.class);
	private static final UserService userService = service.getUserService();
	private static final EntityParser parser = EntityParserImpl.getInstance();
	private static final List<UserRole> allowedRolesList = new ArrayList<>();
		
	static {
		allowedRolesList.add(UserRole.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
		User user = (User)request.getSession().getAttribute(ContextParams.Session.USER);
		request.setAttribute(ContextParams.Request.SELECT_MODE, request.getParameter(HttpParams.SELECT_MODE));
		if (user == null) {
			throw new NotAuthorizedException();
		}
		if (!allowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(allowedRolesList);
		}
		List<User> userList = null;
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			userList = userService.getByFilter((User)request.getSession().getAttribute(ContextParams.Session.USER), filter);
			request.removeAttribute(ContextParams.Request.ENTITIES_LIST);
			request.setAttribute(ContextParams.Request.ENTITIES_LIST, userList);
			return JspPaths.USERS;
		} catch (by.epam.training.studentcourses.service.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		} catch (InternalServiceException e) {
			throw new InternalControllerException(e);
		} catch (by.epam.training.studentcourses.service.exception.NotAllowedException e) {
			throw new NotAllowedException(e.getAllowedRolesList(), e);
		}

	}

}
