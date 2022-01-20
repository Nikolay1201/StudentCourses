package by.epam.training.studentcourses.dao.exception;

public class DAOException extends Exception {
	
	public DAOException() {}
	
	public DAOException(Throwable e) {
		super(e);
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(String message, Throwable e) {
		super(message, e);
	}
	
}
