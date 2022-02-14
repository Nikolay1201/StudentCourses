package by.epam.training.studentcourses.controller.exception;

public class NotAuthException extends ControllerException {

	public NotAuthException() {
	}

	public NotAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public NotAuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAuthException(String message) {
		super(message);
	}

	public NotAuthException(Throwable cause) {
		super(cause);
	}

}
