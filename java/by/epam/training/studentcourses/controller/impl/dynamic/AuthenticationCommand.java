package by.epam.training.studentcourses.controller.impl.dynamic;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.impl.ErrorMessages;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.service.exception.WrongPasswordException;
import by.epam.training.studentcourses.util.UseSessionInfo;

public class AuthenticationCommand implements Command {

	private static Logger log = LogManager.getLogger(AuthenticationCommand.class);
	public static final UserService userService = service.getUserService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		ErrorMessages err = new ErrorMessages(request);
		String login = request.getParameter(HttpParams.Authentification.LOGIN);
		String password = request.getParameter(HttpParams.Authentification.PASSWORD);
		if (login == null || password == null) {
			throw new InvalidRequestException();
		}
		try {
			if (request.getSession().getAttribute(ContextParams.Session.USER) == null) {
				UseSessionInfo userSessionInfo = userService.authorize(login, password);
				request.getSession().setAttribute(ContextParams.Session.USER, userSessionInfo.getUser());
				request.getSession().setAttribute(ContextParams.Session.LOGIN, userSessionInfo.getUser().getLogin());
				log.debug(userSessionInfo.getUser());
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (NoSuchEntityException e) {
			log.debug("auth wrong login: {}, password: {}", login, password);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(err.invalidLogin());
		} catch (WrongPasswordException e) {
			log.debug("auth login {}, wrong password: {}", login, password);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(err.invalidPassword());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return null;
	}

}
