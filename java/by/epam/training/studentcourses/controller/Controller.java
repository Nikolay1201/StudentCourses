package by.epam.training.studentcourses.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.exception.NotAuthorizedException;
import by.epam.training.studentcourses.controller.exception.ResourseNotFoundException;
import by.epam.training.studentcourses.controller.impl.ErrorMessages;
import by.epam.training.studentcourses.service.Service;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.exception.ServiceException;

public class Controller extends HttpServlet {

	private static Logger log = LogManager.getLogger(Controller.class);
	private static Service service = ServiceFactory.getInstance();

	@Override
	public void init() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override 
			public void run()  {
				try {
					service.close();
				} catch (ServiceException e) { }
			}
		});
		try {
			super.init();
			ServletContext sc = getServletContext();
//			sc.setAttribute(ContextParams.Servlet.LOCALSTORAGE_PARAM_ENTITY_ID,
//					ContextParams.Servlet.LOCALSTORAGE_PARAM_ENTITY_ID);
//			sc.setAttribute(ContextParams.Servlet.LOCALSTORAGE_PARAM_ENTITY_DESCR,
//					ContextParams.Servlet.LOCALSTORAGE_PARAM_ENTITY_DESCR);
			Configurator.setRootLevel(Level.TRACE);
			Invoker.init();
			service.init();
			log.trace("controller is initialized");
		} catch (ServiceException | ServletException e) {
			log.fatal(e);
		}
	}

	@Override
	public void destroy() {
		try {
			super.destroy();
			service.close();
		} catch (ServiceException e) {
			log.error(e);
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ErrorMessages err = new ErrorMessages(request);
		String errorMessage = err.unknownError();
		log.trace("{}?{}", request.getRequestURL(), request.getQueryString());
		StringBuilder str = new StringBuilder();
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			str.append("\n");
			str.append(entry.getKey());
			str.append(": ");
			str.append(entry.getValue()[0]);
		}
		log.trace(str);
		try {
			String jspPath = Invoker.execute(request.getMethod() + request.getServletPath() + request.getPathInfo(),
					request, response);
			log.debug("jsp path: {}", jspPath);
			if (jspPath != null) {
				request.getRequestDispatcher("/WEB-INF/" + jspPath + ".jsp").forward(request, response);
			}
			return;
		} catch (NotAllowedException e) {
			log.debug(e);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			errorMessage = err.notAllowed(e.getAllowedRolesList());
		} catch (NotAuthorizedException e) {
			log.debug(e);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			errorMessage = err.notAuthorized();
		} catch (ResourseNotFoundException e) {
			log.debug(e);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			errorMessage = err.pageNotFound();
		} catch (InvalidRequestException e) {
			log.debug(e);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			errorMessage = err.invalidRequest(e.getMessage());
		} catch (InternalControllerException e) {
			log.error(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			errorMessage = err.internalError();
		} catch (ControllerException e) {
			log.warn(e);
			response.setStatus(HttpParams.StatusCode.UNKNOWN_ERROR);
			errorMessage = err.unknownError();
		}
		request.setAttribute(ContextParams.Request.Error.MESSAGE, errorMessage);
		request.getRequestDispatcher("/WEB-INF" + JspPaths.ERROR + ".jsp").forward(request, response);
	}

}
