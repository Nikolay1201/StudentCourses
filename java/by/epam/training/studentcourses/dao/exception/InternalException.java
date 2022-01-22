package by.epam.training.studentcourses.dao.exception;

public class InternalException extends DAOException {
	
	public InternalException() {}
	
	public InternalException(Throwable e) {
		super(e);
	}
	
	public InternalException(String message) {
		super(message);
	}
	
	public InternalException(String message, Throwable e) {
		super(message, e);
	}

}
