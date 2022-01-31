package by.epam.training.studentcourses.util;

import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.SessionToken;

public class UseSessionInfo {
	private User user;
	private SessionToken userSessionToken;
	
	public UseSessionInfo(User user, SessionToken userSessionToken) {
		this.user = user;
		this.userSessionToken = userSessionToken;
	}
	
	public User getUser() {
		return user;
	}
	
	public SessionToken getSessionToken() {
		return userSessionToken;
	}
	
	

}
