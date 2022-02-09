package by.epam.training.studentcourses.dao;

import java.util.List;

import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.dao.exception.InvalidEntityException;
import by.epam.training.studentcourses.dao.exception.InvalidRequestException;
import by.epam.training.studentcourses.dao.exception.NoSuchEntityException;
import by.epam.training.studentcourses.util.Filter;

public interface EntityDAO<T> {
	List<Integer> add(List<T> entityList) throws InvalidEntityException, InternalDAOException;
	Integer add(T entity) throws NoSuchEntityException, InvalidEntityException, InternalDAOException;
	List<T> getByFilter(Filter filter) throws InvalidRequestException, InternalDAOException;
	T getById(Integer id) throws NoSuchEntityException, InvalidRequestException, InternalDAOException;
	void update(List<T> entityList) throws InvalidEntityException, InternalDAOException;
	void update(T entity) throws InvalidEntityException, InternalDAOException;
	@Deprecated(forRemoval = false)
	void deleteByIdsListCascade(List<Integer> entitiesIdsList) throws InvalidRequestException, InternalDAOException;
	@Deprecated(forRemoval = false)
	void deleteByIdCascade(Integer id) throws InvalidRequestException, InternalDAOException;
}
