package by.epam.training.studentcourses.service;

import java.util.List;

import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.User;

public interface CourseService extends EntityCRUDService<Course> {

	List<Course> getCoursesOfUser(User user, User userWithCoursesRequired);

}
