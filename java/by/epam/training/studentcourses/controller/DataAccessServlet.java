package by.epam.training.studentcourses.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.RequestParams;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.Service;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.exception.ServiceException;

public class DataAccessServlet extends HttpServlet {
	
	private static Service service = ServiceFactory.getInstance();
	private static Logger logger = LogManager.getLogger(DataAccessServlet.class);
	
	private void sendErrorMessage(String message, int statusCode, HttpServletResponse response) throws IOException {
		response.setStatus(statusCode);
		sendErrorMessage(message, response);
	}
	
	private void sendErrorMessage(String message, HttpServletResponse response) throws IOException {
		response.getWriter().append(message);
	}
	
	@Override 
	public void init() {
		try {
			service.init();
		} catch (ServiceException e) {
			logger.fatal(e);
		}
	}
	
	@Override 
	public void destroy() {
		try {
			service.close();
		} catch (ServiceException e) {
			logger.error(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8"); //crutch
		System.out.println(request.getRequestURL() + request.getQueryString());
		try {
			Invoker.execute(request.getParameter(RequestParams.OPERATION_TYPE), request, response);
		} catch (InvalidRequestException e) {
			sendErrorMessage(e.getLocalizedMessage(), response);
			logger.debug(e);
		} catch (ControllerException e) {
			sendErrorMessage(ErrorMessages.INTERNAL_ERROR, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
			logger.error(e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
	
}
