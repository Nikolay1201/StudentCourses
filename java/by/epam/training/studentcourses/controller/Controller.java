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
import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.service.Service;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.exception.ServiceException;

public class Controller extends HttpServlet {

	private static Logger log = LogManager.getLogger(Controller.class);
	private static Service service = ServiceFactory.getInstance();

	@Override
	public void init() {
		try {
			super.init();
			ServletContext sc = getServletContext();
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
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.trace("{}?{}", request.getRequestURL(), request.getQueryString());
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			log.trace("{}: {}", entry.getKey(), entry.getValue()[0]);
		}
		try {
			String jspPath = Invoker.execute(
					request.getMethod() + request.getServletPath() + request.getPathInfo(), request, response);
			log.debug("jsp path: {}", jspPath);
			if (jspPath != null) {
				request.getRequestDispatcher("/WEB-INF/" + jspPath + ".jsp").forward(request, response);
			}
			return;
		} catch(NotAllowedException e) {
			log.debug("not allowed", e);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			request.setAttribute(ContextParams.Session.ERROR_MESSAGE,
					ErrorMessages.Authorization.NOT_ALLOWED);
		} catch(ControllerException e) {
			log.error("internal error", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			request.setAttribute(ContextParams.Session.ERROR_MESSAGE,
					ErrorMessages.INTERNAL_ERROR);
		}
		request.getRequestDispatcher("/WEB-INF/" + JspPaths.ERROR + ".jsp").forward(request, response);
	}

}
