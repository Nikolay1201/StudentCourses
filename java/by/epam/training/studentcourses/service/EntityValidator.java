package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.util.TableAttr;

public interface EntityValidator<T> {
	
	List<TableAttr> validate(T entitity, boolean skipNull) throws InternalServiceException;
	
}
