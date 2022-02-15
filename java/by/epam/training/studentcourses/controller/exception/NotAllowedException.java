package by.epam.training.studentcourses.controller.exception;

import java.util.List;

import by.epam.training.studentcourses.util.entity.UserRole;

public class NotAllowedException extends ControllerException {
	
	private List<UserRole> allowedRolesList;

	public NotAllowedException(List<UserRole> allowedRolesList) {
		this.allowedRolesList = allowedRolesList;
	}

	public NotAllowedException(List<UserRole> allowedRolesList, Exception e) {
		super(e);
	}

	public List<UserRole> getAllowedRolesList() {
		return allowedRolesList;
	}


}
