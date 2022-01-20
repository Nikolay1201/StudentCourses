package by.epam.training.studentcourses.dao;

import java.util.List;

import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.util.Filter;

public interface EntityDAO<T> {
	void add(List<T> usersList) throws DAOException;
	List<T> getByFilter(Filter filter) throws DAOException;
	void update(List<T> usersList) throws DAOException;
	void deleteCascade(List<T> usersList) throws DAOException;
}
