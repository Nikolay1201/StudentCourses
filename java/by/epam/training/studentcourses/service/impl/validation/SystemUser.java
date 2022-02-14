package by.epam.training.studentcourses.service.impl.validation;

import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class SystemUser {
	private final static User SYSTEM;
	
	static {
		SYSTEM = new User();
		SYSTEM.setRole(UserRole.SYSTEM);
	}
	
	public static User getSystem() {
		return SYSTEM;
	}
}
