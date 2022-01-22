package by.epam.training.studentcourses.dao.exception;

public class InvalidRequestException extends DAOException {
	
	public InvalidRequestException(Throwable e) {
		super(e);
	}
	
	public InvalidRequestException(String message, Throwable e) {
		super(message, e);
	}
	
}
