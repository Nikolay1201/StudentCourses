package by.epam.training.studentcourses.service.exception;

public class EntityAlreadyExistsException extends ServiceException {
	private final Object entity;
	
	public EntityAlreadyExistsException(Object entity) {
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
	
}
