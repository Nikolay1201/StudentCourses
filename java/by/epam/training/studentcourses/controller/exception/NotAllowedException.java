package by.epam.training.studentcourses.controller.exception;

public class NotAllowedException extends ControllerException {

	public NotAllowedException() {

	}

	public NotAllowedException(String message) {
		super(message);

	}

	public NotAllowedException(Throwable cause) {
		super(cause);

	}

	public NotAllowedException(String message, Throwable cause) {
		super(message, cause);

	}

	public NotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
