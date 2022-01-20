package by.epam.training.studentcourses.dao.exception;

public class InvalidEntityException extends DAOException {
	private Object entity;
	
	public InvalidEntityException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public InvalidEntityException(Object entity) {
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
}
