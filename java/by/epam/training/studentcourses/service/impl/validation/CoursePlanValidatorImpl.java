package by.epam.training.studentcourses.service.impl.validation;
import java.util.ArrayList;
import java.util.List;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.CoursePlan;

public class CoursePlanValidatorImpl implements EntityValidator<CoursePlan> {
	
	@Override
	public List<TableAttr> validate(CoursePlan coursePlan, boolean skipNull) {
		List<TableAttr> invalidAttrList = new ArrayList<>();
		if (coursePlan.getCourseId() == null && !skipNull) {
			invalidAttrList.add(Tables.CoursesPlans.Attr.COURSE_ID);
		}
		if (coursePlan.getStatus() == null && !skipNull) {
			invalidAttrList.add(Tables.CoursesPlans.Attr.STATUS_ID);
		}
		if (coursePlan.getTrainerUserId() == null && !skipNull) {
			invalidAttrList.add(Tables.CoursesPlans.Attr.TRAINER_USER_ID);
		}
		return new ArrayList<>();
	}
	
}
