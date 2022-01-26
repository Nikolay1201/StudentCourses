package by.epam.training.studentcourses.util.entity;

import java.util.NoSuchElementException;

public enum UserRole {
	SYSTEM(0),
	ADMIN(1),
	TRAINER(2),
	STUDENT(3);
	
	private int id;
	
	private UserRole(int id) {
		this.id = id;
	}
	
	public int getId() {
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
