package by.epam.training.studentcourses.service.exception;

public class InvalidRequestException extends ServiceException {
	private String invalidRequest;
	
	public InvalidRequestException(String invalidRequest) {
		this.invalidRequest = invalidRequest;
	}
	
	public InvalidRequestException(String invalidRequest, String message) {
		super(message);
		this.invalidRequest = invalidRequest;
	}
	
	public InvalidRequestException(String invalidRequest, Throwable e, String message) {
		super(message, e);
		this.invalidRequest = invalidRequest;
	}
	
	public InvalidRequestException(Exception e) {
		super(e);
	}	
	
	public String getRequest() {
		return invalidRequest;
	}
}
