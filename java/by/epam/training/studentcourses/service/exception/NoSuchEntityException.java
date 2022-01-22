package by.epam.training.studentcourses.service.exception;

public class NoSuchEntityException extends ServiceException {
	public NoSuchEntityException(Throwable e) {
		super(e);
	}
	
	public NoSuchEntityException() {}
}
