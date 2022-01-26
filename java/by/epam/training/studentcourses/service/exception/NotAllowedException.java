package by.epam.training.studentcourses.service.exception;

import java.util.Arrays;
import java.util.List;

import by.epam.training.studentcourses.util.entity.UserRole;

public class NotAllowedException extends ServiceException {
	private List<UserRole> requiredRolesList = null;
	
	public NotAllowedException(List<UserRole> requiredRolesList) {
		this.requiredRolesList = requiredRolesList;
	}
	
	public NotAllowedException(UserRole requiredRole) {
		this.requiredRolesList = Arrays.asList(requiredRole);
	}
	
	public NotAllowedException() {
		
	}
	
	public List<UserRole> getAllowedRolesList() {
		return requiredRolesList;
	}
}
