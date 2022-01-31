package by.epam.training.studentcourses.service.impl.validation;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;

public class StudentsHaveCoursesPlansValidatorImpl implements EntityValidator<StudentsHaveCoursesPlans> {

	@Override
	public Tables.StudentsHaveCoursesPlans.Attr validate(StudentsHaveCoursesPlans entity) {
		if (entity.getId() != null &&
				entity.getCoursePlanId() != null &&
				entity.getStudentId() != null &&
				entity.getReview() != null &&
				entity.getMark() != null &&
				entity.getMark() >= 0 &&
				entity.getMark() <= 10) {
			return Tables.StudentsHaveCoursesPlans.Attr.ID;
		}
			return null;
	}

}
