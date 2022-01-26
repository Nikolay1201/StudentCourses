package by.epam.training.studentcourses.service;

import by.epam.training.studentcourses.service.impl.ServiceImpl;

public class ServiceFactory {
	private static Service instance = new ServiceImpl();
	
	public static Service getInstance() {
		return instance;
	}
	
	private ServiceFactory() {}
}
