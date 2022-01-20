package by.epam.training.studentcourses.dao.exception;

public class InvalidRequestException extends DAOException {
	private String invalidRequest;
	
	public InvalidRequestException(String invalidRequest) {
		this.invalidRequest = invalidRequest;
	}
	
	public InvalidRequestException(String invalidRequest, Throwable e, String message) {
		super(message, e);
		this.invalidRequest = invalidRequest;
	}
	
	public String getRequest() {
		return invalidRequest;
	}
}
