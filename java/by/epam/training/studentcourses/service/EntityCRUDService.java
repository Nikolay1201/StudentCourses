package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidEntitiesException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.User;

public interface EntityCRUDService<T> {

	void add(User user, List<T> entitiesList)
			throws InternalServiceException, NotAllowedException, InvalidEntitiesException;

	void add(User user, T entity) throws InternalServiceException, NotAllowedException, InvalidEntitiesException;

	List<T> getByFilter(User user, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException;

	T getById(User user, Integer id)
			throws InvalidRequestException, InternalServiceException, NoSuchEntityException, NotAllowedException;

	void update(User user, List<T> entitiesList)
			throws InvalidEntitiesException, InternalServiceException, NotAllowedException;

	void update(User user, T entity) throws InvalidEntitiesException, InternalServiceException, NotAllowedException;

	void deleteByIdsList(User user, List<Integer> entitiesList)
			throws InvalidRequestException, NotAllowedException, InternalServiceException;

	void deleteById(User user, Integer id)
			throws InvalidRequestException, NotAllowedException, InternalServiceException;

}
