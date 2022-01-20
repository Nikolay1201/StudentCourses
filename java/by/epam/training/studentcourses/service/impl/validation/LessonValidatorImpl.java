package by.epam.training.studentcourses.service.impl.validation;

import java.time.LocalDateTime;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Lesson;

public class LessonValidatorImpl implements EntityValidator<Lesson> {
	
	private static final int maxPlannablePeriodInMonth = 6;
	
	@Override
	public TableAttr validate(Lesson lesson) {
		if (!validateClassroomNumber(lesson.getClassroomNumber())) {
			return Tables.Lessons.Attr.CLASSROOM_NUMBER;
		}
		if (!validateStartTime(lesson.getStartTime())) {
			return Tables.Lessons.Attr.START_TIME;
		}
		return null;
	}
	
	private boolean validateClassroomNumber(Integer classRoomNumber) {
		return (classRoomNumber >= 100 && classRoomNumber <= 777);
	}
	
	private boolean validateStartTime(LocalDateTime startTime) {
		return (startTime.compareTo(LocalDateTime.now()) >= 0 &&
				startTime.compareTo(LocalDateTime.now().minusMonths(maxPlannablePeriodInMonth)) <= 0);
	}
}
