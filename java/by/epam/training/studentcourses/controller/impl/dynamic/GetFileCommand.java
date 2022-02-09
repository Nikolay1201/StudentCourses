package by.epam.training.studentcourses.controller.impl.dynamic;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.exception.ControllerException;

public class GetFileCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		System.out.println("get file");
		BufferedReader br = request.getReader();
		String line;
		request.getInputStream();
		do {
			line = br.readLine();
			System.out.println(line);
		} while (line != null);
		return null;
	}
	
}
