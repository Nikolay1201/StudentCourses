package by.epam.training.studentcourses.controller.impl.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.NotAuthorizedException;
import by.epam.training.studentcourses.util.entity.User;

public class GotoProfilePageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException {

		User user = (User) request.getSession().getAttribute(ContextParams.Session.USER);
		if (user == null) {
			throw new NotAuthorizedException();
		}
		return JspPaths.PROFILE;
	}

}
