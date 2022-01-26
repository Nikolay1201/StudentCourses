package by.epam.training.studentcourses.dao;

import java.util.List;

import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.util.Filter;

public interface EntityDAO<T> {
	List<Integer> add(List<T> entityList) throws DAOException;
	Integer add(T entity) throws DAOException;
	List<T> getByFilter(Filter filter) throws DAOException;
	T getById(Integer id) throws DAOException;
	void update(List<T> entityList) throws DAOException;
	void update(T entity) throws DAOException;
	@Deprecated(forRemoval = false)
	void deleteByIdsListCascade(List<Integer> entitiesIdsList) throws DAOException;
	@Deprecated(forRemoval = false)
	void deleteByIdCascade(Integer id) throws DAOException;
}
