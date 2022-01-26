package by.epam.training.studentcourses.util;

import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserSessionToken;

public class UseSessionInfo {
	private User user;
	private UserSessionToken userSessionToken;
	
	public UseSessionInfo(User user, UserSessionToken userSessionToken) {
		this.user = user;
		this.userSessionToken = userSessionToken;
	}
	
	public User getUser() {
		return user;
	}
	
	public UserSessionToken getUserSessionToken() {
		return userSessionToken;
	}
	
	

}
