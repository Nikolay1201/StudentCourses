package by.epam.training.studentcourses.service.impl.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Lesson;

public class LessonValidatorImpl implements EntityValidator<Lesson> {

	@Override
	public List<TableAttr> validate(Lesson lesson, boolean skipNull) {
		List<TableAttr> invalidAttrList = new ArrayList<>();
		if ((lesson.getClassroomNumber() == null && !skipNull)
				|| (lesson.getClassroomNumber() != null && !validateClassroomNumber(lesson.getClassroomNumber()))) {
			invalidAttrList.add(Tables.Lessons.Attr.CLASSROOM_NUMBER);
		}
		if ((lesson.getStartTime() == null && !skipNull)
				|| (lesson.getStartTime() != null && !validateStartTime(lesson.getStartTime()))) {
			invalidAttrList.add(Tables.Lessons.Attr.START_TIME);
		}
		return invalidAttrList;
	}

	private boolean validateClassroomNumber(Integer classRoomNumber) {
		return (classRoomNumber >= 0 && classRoomNumber <= 999);
	}

	private boolean validateStartTime(LocalDateTime startTime) {
		return (startTime.compareTo(LocalDateTime.now()) >= 0);
	}
}
