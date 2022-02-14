package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.User;

public interface CoursePlanService extends EntityCRUDService<CoursePlan> {

	List<CoursePlan> getStudentCoursePlans(User user, User student, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException;

	List<CoursePlan> getTrainerCoursePlans(User user, User trainer, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException;

}
