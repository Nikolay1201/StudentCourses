package by.epam.training.studentcourses.controller.impl.dynamic;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;

public class ChangeLocaleCommand implements Command {
	
	private static Logger log = LogManager.getLogger(ChangeLocaleCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
		String newLangName = request.getParameter(HttpParams.LANG);
		if (newLangName == null) {
			throw new InvalidRequestException();
		}
		request.getSession().setAttribute(ContextParams.Session.LOCALE, new Locale(newLangName));
		log.debug("new lang: {}", newLangName);
		return null;
	}

}
