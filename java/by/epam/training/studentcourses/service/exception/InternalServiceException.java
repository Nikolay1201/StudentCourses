package by.epam.training.studentcourses.service.exception;

public class InternalServiceException extends ServiceException {
	
	public InternalServiceException() {}
	
	public InternalServiceException(Throwable e) {
		super(e);
	}
	
	public InternalServiceException(String message) {
		super(message);
	}
	
	public InternalServiceException(String message, Throwable e) {
		super(message, e);
	}

}
