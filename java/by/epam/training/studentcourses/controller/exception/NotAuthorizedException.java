package by.epam.training.studentcourses.controller.exception;

public class NotAuthorizedException extends ControllerException {

	public NotAuthorizedException() {
	}

	public NotAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public NotAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAuthorizedException(String message) {
		super(message);
	}

	public NotAuthorizedException(Throwable cause) {
		super(cause);
	}

}
