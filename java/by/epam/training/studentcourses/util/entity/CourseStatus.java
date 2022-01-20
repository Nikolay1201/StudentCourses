package by.epam.training.studentcourses.util.entity;

import java.util.NoSuchElementException;

public enum CourseStatus {
	NOT_STARTED(1),
	ACTIVE(2),
	SUSPENDED(3),
	FINISHED(4),
	CANCELLED(5);
	
	private int value;
	
	private CourseStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static CourseStatus getByVal(int valueRequired) {
		for (int i = 0; i < CourseStatus.values().length; i ++) {
			if (CourseStatus.values()[i].getValue() == valueRequired) {
				return CourseStatus.values()[i];
			}
		}
		throw new NoSuchElementException();
	}
}