package by.epam.training.studentcourses.controller.exception;

public class InternalControllerException extends ControllerException {

	public InternalControllerException() {}

	public InternalControllerException(String message) {
		super(message);
	}

	public InternalControllerException(Throwable cause) {
		super(cause);
	}

	public InternalControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalControllerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
