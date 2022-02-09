package by.epam.training.studentcourses.service.impl.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;

public class CourseValidatorImpl implements EntityValidator<Course> {

	private static final String NAME_REG_EXP = "[a-zA-Z.,-]+";

	@Override
	public List<TableAttr> validate(Course course, boolean skipNull) {
		List<TableAttr> invalidAttrList = new ArrayList<>();
		if ((course.getName() == null && !skipNull) || 
				(course.getName() != null && !validateName(course.getName()))) {
			invalidAttrList.add(Tables.Courses.Attr.NAME);
		}
		if (course.getDuration() == null && !skipNull) {
			invalidAttrList.add(Tables.Courses.Attr.DURATION);
		}
		return invalidAttrList;
	}

	private boolean validateName(String name) {
		return !name.isEmpty();
	}
}
