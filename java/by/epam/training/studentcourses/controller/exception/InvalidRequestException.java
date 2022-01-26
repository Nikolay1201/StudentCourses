package by.epam.training.studentcourses.controller.exception;

public class InvalidRequestException extends ControllerException {

	public InvalidRequestException() {
		super();
	}

	public InvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRequestException(String message) {
		super(message);
	}

	public InvalidRequestException(Throwable cause) {
		super(cause);
	}
	
}
