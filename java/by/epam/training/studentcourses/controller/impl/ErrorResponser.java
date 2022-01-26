package by.epam.training.studentcourses.controller.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ErrorResponser {

	public static void message(String message, int statusCode, HttpServletResponse response) throws IOException {
		response.setStatus(statusCode);
		message(message, response);
	}
	
	public static void message(String message, HttpServletResponse response) throws IOException {
		response.getWriter().append(message);
	}
	
	private ErrorResponser() {}

}
