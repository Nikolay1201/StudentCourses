package by.epam.training.studentcourses.dao;

import java.util.List;

import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.dao.exception.InvalidEntityException;
import by.epam.training.studentcourses.dao.exception.InvalidRequestException;
import by.epam.training.studentcourses.dao.exception.NoSuchEntityException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.Identifiable;

/**
 * Generalization of all CRUD methods available for any entity in the database.
 * <p>Such a generalization is possible since every entity class implements 
 * {@link by.epam.training.studentcourses.util.Identifiable} interface,
 * which allows go get\set the ID of any entity
 * @param T entity class
 */
public interface EntityDAO<T extends Identifiable> {
	/**
	 * Adds a list of entities T to the database
	 * <p>If the list is empty, nothing will happen. 
	 * <p>
	 * @param entitiesList - {@code List} of entities being added
	 * @return list of new IDs of entities added
	 * @throws InvalidEntityException if any entity from the list turned out to be invalid
	 * @throws InternalDAOException if any internal exception is occurred
	 */
	List<Integer> add(List<T> entitiesList) throws InvalidEntityException, InternalDAOException;
	/**
	 * see {@link #add(List)}
	 * @param entity being added
	 */
	Integer add(T entity) throws NoSuchEntityException, InvalidEntityException, InternalDAOException;
	/**
	 * Searches entities T in the database which match specified filter
	 * @param filter - filter which contains conditions for entities being searched
	 * @return List of entities T
	 * @throws InvalidRequestException if the filter turned out to be invalid
	 * @throws InternalDAOException if any internal exception is occurred
	 * @see {@link by.epam.training.studentcourses.util.Filter}
	 */
	List<T> getByFilter(Filter filter) throws InvalidRequestException, InternalDAOException;
	/**
	 * Searches entity T in the database with specific ID
	 * @param id ID of the entity being searched
	 * @return entity T
	 * @throws NoSuchEntityException if the database contains no entity with specified ID
	 * @throws InvalidRequestException
	 * @throws InternalDAOException if any internal exception is occurred
	 * @see #getByFilter(Filter)
	 */
	T getById(Integer id) throws NoSuchEntityException, InvalidRequestException, InternalDAOException;
	/**
	 * Updates data of all entities in the list. 
	 * <p>The method finds entities in the database which contains the same id as in the list and 
	 * replace all corresponding fields. If any filed of entity in the list is null, the method doesn't update it.
	 * @param entityList list of entities being updated
	 * @throws InvalidEntityException if any entity turned out to be invalid
	 * @throws InternalDAOException if any internal exception is occurred
	 */
	void update(List<T> entityList) throws InvalidEntityException, InternalDAOException;
	/**
	 * See @{@link #update(List)}
	 */
	void update(T entity) throws InvalidEntityException, InternalDAOException;
	/**
	 * Removes entities and all entities connected with id from the database (in cascade way).
	 * @param entitiesIdsList list of IDs of entities being removed
	 * @throws InvalidRequestException if any entity with the specified ID doesn't exist
	 * @throws InternalDAOException if any internal exception is occurred
	 */
	@Deprecated(forRemoval = false)
	void deleteByIdsListCascade(List<Integer> entitiesIdsList) throws InvalidRequestException, InternalDAOException;
	/**
	 * See {@link #deleteByIdsListCascade(List)}
	 */
	@Deprecated(forRemoval = false)
	void deleteByIdCascade(Integer id) throws InvalidRequestException, InternalDAOException;
}
