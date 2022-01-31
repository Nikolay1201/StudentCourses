package by.epam.training.studentcourses.controller.impl.dynamic;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.service.exception.WrongPasswordException;
import by.epam.training.studentcourses.util.UseSessionInfo;

//TODO change to normal auth by std means of http
public class AuthenticationCommand implements Command {

	private static Logger log = LogManager.getLogger(AuthenticationCommand.class);
	public static final UserService userService = service.getUserService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		String login = request.getParameter(HttpParams.Authentification.LOGIN);
		String password = request.getParameter(HttpParams.Authentification.PASSWORD);
		if (login == null || password == null) {
			throw new InvalidRequestException();
		}
		try {
			UseSessionInfo userSessionInfo = userService.authorize(login, password);
			request.getSession().setAttribute(ContextParams.Session.USER, userSessionInfo.getUser());
			request.getSession().setAttribute(ContextParams.Session.LOGIN, userSessionInfo.getUser().getLogin());
			// add session_token to user's localstorage by JS
			response.setStatus(HttpServletResponse.SC_OK);
			return JspPaths.PROFILE;
		} catch (NoSuchEntityException e) {
			log.debug("auth wrong login: {}, password: {}", login, password);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			request.setAttribute(ContextParams.Session.ERROR_MESSAGE,
					ErrorMessages.Authorization.INVALID_LOGIN);
		} catch (WrongPasswordException e) {
			log.debug("auth login {}, wrong password: {}", login, password);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			request.setAttribute(ContextParams.Session.ERROR_MESSAGE,
					ErrorMessages.Authorization.INVALID_PASSWORD);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return JspPaths.ERROR;
	}

}
