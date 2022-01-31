package by.epam.training.studentcourses.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.service.Service;
import by.epam.training.studentcourses.service.ServiceFactory;

public interface Command {
	
	public static Service service = ServiceFactory.getInstance();
	String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ControllerException, IOException;
}
