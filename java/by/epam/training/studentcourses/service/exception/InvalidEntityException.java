package by.epam.training.studentcourses.service.exception;

public class InvalidEntityException extends ServiceException {
	private final Object entity;
	
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
	
	public InvalidEntityException() {}
}
