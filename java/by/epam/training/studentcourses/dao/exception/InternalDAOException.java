package by.epam.training.studentcourses.dao.exception;

public class InternalDAOException extends DAOException {
	
	public InternalDAOException() {}
	
	public InternalDAOException(Throwable e) {
		super(e);
	}
	
	public InternalDAOException(String message) {
		super(message);
	}
	
	public InternalDAOException(String message, Throwable e) {
		super(message, e);
	}

}
