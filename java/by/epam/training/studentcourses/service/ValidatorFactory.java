package by.epam.training.studentcourses.service;

import by.epam.training.studentcourses.service.impl.validation.CoursePlanValidatorImpl;
import by.epam.training.studentcourses.service.impl.validation.CourseValidatorImpl;
import by.epam.training.studentcourses.service.impl.validation.LessonValidatorImpl;
import by.epam.training.studentcourses.service.impl.validation.StudentsHaveCoursesPlansValidatorImpl;
import by.epam.training.studentcourses.service.impl.validation.UserValidatorImpl;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;

public class ValidatorFactory {
	private static EntityValidator<User> userValidatorInstance = new UserValidatorImpl();
	private static EntityValidator<Course> courseValidatorInstance = new CourseValidatorImpl();
	private static EntityValidator<CoursePlan> coursePlanValidatorInstance = new CoursePlanValidatorImpl();
	private static EntityValidator<Lesson> lessonValidatroInstance = new LessonValidatorImpl();
	private static EntityValidator<StudentsHaveCoursesPlans> studentsHaveCoursesPlansValidator = 
			new StudentsHaveCoursesPlansValidatorImpl();

	private ValidatorFactory() {
	}

	public static EntityValidator<User> getUserValidator() {
		return userValidatorInstance;
	}

	public static EntityValidator<Course> getCourseValidator() {
		return courseValidatorInstance;
	}

	public static EntityValidator<CoursePlan> getCoursePlanValidator() {
		return coursePlanValidatorInstance;
	}

	public static EntityValidator<Lesson> getLessonValidator() {
		return lessonValidatroInstance;
	}
	
	public static EntityValidator<StudentsHaveCoursesPlans> getStudentsHaveCoursesPlansValidator() {
		return studentsHaveCoursesPlansValidator;
	}
}
