package by.epam.training.studentcourses.dao.exception;

public class EntityAlreadyExistsException extends DAOException {
	private final Object entity;
	
	public EntityAlreadyExistsException(Object entity) {
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
	
}
