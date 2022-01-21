package by.epam.training.studentcourses.util;

import java.util.NoSuchElementException;

public enum FiltrationType {
	LIKE("LIKE"),
	GREATER_THAN(">"),
	LESS_THAN("<"),
	EQUALS("=");
	
	private String value;
	
	private FiltrationType(String value) {
		this.value = value;
	}
	
	public String getStringRepr() {
		return value;
	}
	
	public static FiltrationType getByTextRepr(String textRepr) {
		for (int i = 0; i < FiltrationType.values().length; i ++) {
			if (FiltrationType.values()[i].getStringRepr().equals(textRepr)) {
				return FiltrationType.values()[i];
			}
		}
		throw new NoSuchElementException();
	}
}
