package by.epam.training.studentcourses.service;

import by.epam.training.studentcourses.util.TableAttr;

public interface EntityValidator<T> {
	
	TableAttr validate(T entitity);
	
}
