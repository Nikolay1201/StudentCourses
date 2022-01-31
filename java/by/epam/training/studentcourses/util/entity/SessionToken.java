package by.epam.training.studentcourses.util.entity;

import by.epam.training.studentcourses.util.Identifiable;

public class SessionToken implements Identifiable {
	private Integer id;
	private Integer userId;
	private String sessionTokenValue;
	
	public SessionToken(Integer userId, String sessionToken) {
		this.setUserId(userId);
		this.sessionTokenValue = sessionToken;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getValue() {
		return sessionTokenValue;
	}
	
	public void setValue(String sessionTokenValue) {
		this.sessionTokenValue = sessionTokenValue;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}	
	
}
