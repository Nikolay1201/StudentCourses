package by.epam.training.studentcourses.util.entity;

import java.util.NoSuchElementException;

public enum UserRole {
	ADMIN(0),
	TRAINER(1),
	STUDENT(2);
	
	private int id;
	
	private UserRole(int id) {
		this.id = id;
	}
	
	public int id() {
		return id;
	}
	
	public static UserRole getById(int id) {
		for (int i = 0; i < UserRole.values().length; i ++) {
			if (UserRole.values()[i].id == id) {
				return UserRole.values()[i];
			}
		} 
		throw new NoSuchElementException();
	}


}
