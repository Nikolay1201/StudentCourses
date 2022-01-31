package by.epam.training.studentcourses.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class CRUDAuthorizator<T> {
	
	protected List<UserRole> anythingAllowedRolesList = new ArrayList<UserRole>();
	
	protected CRUDAuthorizator(UserRole... userRoles) {
		anythingAllowedRolesList.addAll(Arrays.asList(userRoles));
		anythingAllowedRolesList.add(UserRole.SYSTEM);
	}
	
	protected void add(User user, List<T> entitiesList) throws NotAllowedException {
		if (user == null || !anythingAllowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(anythingAllowedRolesList);
		}
	}
	
	protected void getByFilter(User user, Filter filter) throws NotAllowedException {
		if (user == null || !anythingAllowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(anythingAllowedRolesList);
		}
	}
	
	protected void getById(User user, Integer id) throws NotAllowedException {
		if (user == null || !anythingAllowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(anythingAllowedRolesList);
		}
	}
	
	protected void update(User user, List<T> entitiesList) throws NotAllowedException {
		if (user == null || !anythingAllowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(anythingAllowedRolesList);
		}
	}

	protected void deleteByIdsList(User user, List<Integer> entitiesIdsList) throws NotAllowedException {
		if (user == null || !anythingAllowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(anythingAllowedRolesList);
		}
	}
	
	protected void add(User user, T entity) throws NotAllowedException { 
		if (user == null || !anythingAllowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(anythingAllowedRolesList);
		}
	}
	
	protected void update(User user, T entity) throws NotAllowedException {
		if (user == null || !anythingAllowedRolesList.contains(user.getRole())) {
			throw new NotAllowedException(anythingAllowedRolesList);
		}
	}
	
	protected void deleteById(User user, Integer id) throws NotAllowedException {
		deleteByIdsList(user, Arrays.asList(id));
	}
	
}
