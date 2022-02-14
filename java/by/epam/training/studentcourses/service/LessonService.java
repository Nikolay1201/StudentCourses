package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.User;

public interface LessonService extends EntityCRUDService<Lesson> {

	public List<Lesson> getTrainerLessons(User user, User teacher, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException;

	public List<Lesson> getStudentLessons(User user, User student, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException;
}
