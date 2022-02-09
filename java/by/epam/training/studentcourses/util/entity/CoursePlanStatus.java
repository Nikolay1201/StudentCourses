package by.epam.training.studentcourses.util.entity;

import java.util.NoSuchElementException;

public enum CoursePlanStatus {
	NOT_STARTED(1),
	ACTIVE(2),
	SUSPENDED(3),
	FINISHED(4),
	CANCELLED(5);
	
	private int value;
	
	private CoursePlanStatus(int value) {
		this.value = value;	
	}
	
	public int getValue() {
		return value;
	}
	
	public static CoursePlanStatus getByVal(int valueRequired) {
		for (int i = 0; i < CoursePlanStatus.values().length; i ++) {
			if (CoursePlanStatus.values()[i].getValue() == valueRequired) {
				return CoursePlanStatus.values()[i];
			}
		}
		throw new NoSuchElementException();
	}
}