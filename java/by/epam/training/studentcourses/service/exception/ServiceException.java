package by.epam.training.studentcourses.service.exception;

public class ServiceException extends Exception {
	
	public ServiceException() {}
	
	public ServiceException(Throwable e) {
		super(e);
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable e) {
		super(message, e);
	}
	
}
