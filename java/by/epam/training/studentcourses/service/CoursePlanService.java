package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.User;

public interface CoursePlanService extends EntityCRUDService<CoursePlan> {

	List<CoursePlan> getCoursesPlansOfUser(User user, User userRequired);
	
}
