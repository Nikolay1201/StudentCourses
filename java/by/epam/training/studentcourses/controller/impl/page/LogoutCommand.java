package by.epam.training.studentcourses.controller.impl.page;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.impl.dynamic.AuthenticationCommand;
import by.epam.training.studentcourses.service.UserService;

public class LogoutCommand implements Command {

	private static Logger log = LogManager.getLogger(AuthenticationCommand.class);
	public static final UserService userService = service.getUserService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		request.getSession().invalidate();
		return JspPaths.INDEX;
	}

}