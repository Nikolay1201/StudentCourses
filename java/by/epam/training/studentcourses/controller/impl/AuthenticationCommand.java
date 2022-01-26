package by.epam.training.studentcourses.controller.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.RequestParams;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.service.exception.WrongPasswordException;
import by.epam.training.studentcourses.util.UseSessionInfo;

public class AuthenticationCommand implements Command {
	
	//TODO change to normal auth by std means of http
	
	private static Logger log = LogManager.getLogger(AuthenticationCommand.class);
	public static final UserService userService = service.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, IOException {
		String login = request.getParameter(RequestParams.Authentification.LOGIN);
		String password = request.getParameter(RequestParams.Authentification.PASSWORD);		
		if (login == null || password == null) {
			throw new InvalidRequestException();
		}
		System.out.println(login + ", " + password);
		UseSessionInfo userSessionInfo = null;
		try {
			userSessionInfo = userService.authorize(login, password);
		} catch (NoSuchEntityException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			throw new InvalidRequestException(ErrorMessages.Authorization.INVALID_LOGIN);
		} catch (WrongPasswordException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			throw new InvalidRequestException(ErrorMessages.Authorization.INVALID_PASSWORD);
		} catch (ServiceException e) {
			throw new ControllerException();
		}
		System.out.println(userSessionInfo.getUser().toString());
		response.getWriter().append("<h1>" + userSessionInfo.getUser().toString() + "</h1>");
		
	}

}	
