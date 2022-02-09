package by.epam.training.studentcourses.service.impl.validation;

import java.util.ArrayList;
import java.util.List;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;

public class StudentsHaveCoursesPlansValidatorImpl implements EntityValidator<StudentsHaveCoursesPlans> {

	@Override
	public List<TableAttr> validate(StudentsHaveCoursesPlans entity, boolean skipNull) {
		List<TableAttr> invalidAttrList = new ArrayList<>();
		if (entity.getCoursePlanId() == null && !skipNull) {
			invalidAttrList.add(Tables.StudentsHaveCoursesPlans.Attr.COURSE_PLAN_ID);
		}
		if (entity.getStudentUserId() == null && !skipNull) {
			invalidAttrList.add(Tables.StudentsHaveCoursesPlans.Attr.STUDENT_USER_ID);
		}
		if (entity.getMark() != null && !validateMark(entity.getMark())) {
			invalidAttrList.add(Tables.StudentsHaveCoursesPlans.Attr.MARK);
		}
		return invalidAttrList;
	}

	private boolean validateMark(Integer mark) {
		return (mark >= 0 && mark <= 10);
	}

}
