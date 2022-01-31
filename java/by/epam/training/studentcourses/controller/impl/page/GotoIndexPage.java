package by.epam.training.studentcourses.controller.impl.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.constant.JspPaths;

public class GotoIndexPage implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return JspPaths.INDEX;
	}

}
