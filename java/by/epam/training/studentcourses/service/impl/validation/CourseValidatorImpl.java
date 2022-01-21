package by.epam.training.studentcourses.service.impl.validation;

import java.util.regex.Pattern;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;

public class CourseValidatorImpl implements EntityValidator<Course> {
	
	private static final String NAME_REG_EXP = "[a-zA-Z.,-]";
	
	@Override 
	public TableAttr validate(Course course) {
		if (!validateName(course.getName())) {
			return Tables.Courses.Attr.NAME;
		}
		return null;
	}
		
	private boolean validateName(String name) {
		return Pattern.matches(NAME_REG_EXP, name);
	}
}
