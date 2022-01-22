package by.epam.training.studentcourses.util.entity;

import by.epam.training.studentcourses.util.Identifiable;

public class UserSessionToken implements Identifiable {
	private Integer id;
	private Integer userId;
	private String sessionToken;
	
	public UserSessionToken(Integer userId, String sessionToken) {
		this.setUserId(userId);
		this.sessionToken = sessionToken;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSessionToken() {
		return sessionToken;
	}
	
	public void setAuthKey(String authKey) {
		this.sessionToken = authKey;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}	
	
}
