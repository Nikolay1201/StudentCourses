package by.epam.training.studentcourses.controller.impl.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.User;

public class GotoUsersPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoUsersPageCommand.class);
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws InternalControllerException {
		List<User> userList = null;
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			userList = userService.getByFilter((User)request.getSession().getAttribute(ContextParams.Session.USER), filter);
		} catch (ServiceException e) {
			throw new InternalControllerException(e);
		} catch (by.epam.training.studentcourses.controller.exception.InvalidRequestException e) {
			
		}
		request.setAttribute(ContextParams.Request.ENTITIES_LIST, userList);
		return JspPaths.USERS;
	}

}
