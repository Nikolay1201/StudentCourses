package by.epam.training.studentcourses.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.RequestParser;
import by.epam.training.studentcourses.util.entity.User;

public class AddUserCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestParser rp = RequestParserFactory.getInstance();
		List<User> usersList = rp.parseUsers(request.getParameterMap());
		SeriviceFactory.getUserService().add(usersList);
	}

}	
