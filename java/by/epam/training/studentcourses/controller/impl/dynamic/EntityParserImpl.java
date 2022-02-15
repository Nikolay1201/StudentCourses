package by.epam.training.studentcourses.controller.impl.dynamic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.FiltrationType;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.CoursePlanStatus;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class EntityParserImpl implements EntityParser {

	private static EntityParser instance = new EntityParserImpl();

	public static EntityParser getInstance() {
		return instance;
	}

	private EntityParserImpl() {
	}

	@Override
	public List<User> parseUsers(Map<String, String[]> paramsMap) throws InvalidRequestException {
		List<User> usersList = new ArrayList<>();
		String[] idsArr = paramsMap.get(Tables.Users.Attr.USER_ID.getAttrName());
		if (idsArr == null) {
			return usersList;
		}
		User user;
		for (int i = 0; i < idsArr.length; i++) {
			user = new User();

			try {
				user.setId(Integer.parseInt(paramsMap.get(Tables.Users.Attr.USER_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				user.setRole(UserRole.valueOf(paramsMap.get(Tables.Users.Attr.ROLE_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				user.setName(paramsMap.get(Tables.Users.Attr.NAME.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				user.setSurename(paramsMap.get(Tables.Users.Attr.SURENAME.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				user.setPatronymic(paramsMap.get(Tables.Users.Attr.PATRONYMIC.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				user.setBirthDate(LocalDate.parse(paramsMap.get(Tables.Users.Attr.BIRTH_DATE.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				user.setPhoneNumber(paramsMap.get(Tables.Users.Attr.PHONE_NUMBER.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				user.setEmail(paramsMap.get(Tables.Users.Attr.EMAIL.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				user.setLogin(paramsMap.get(Tables.Users.Attr.LOGIN.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				user.setPassword(paramsMap.get(Tables.Users.Attr.PASSWORD.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				user.setDescription((paramsMap.get(Tables.Users.Attr.DESCRIPTION.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			usersList.add(user);
		}
		return usersList;
	}

	@Override
	public Filter parseFilter(Map<String, String[]> paramsMap) throws InvalidRequestException {
		Filter filter = new Filter();
		FiltrationType filtrationType;
		String attrName;
		String attrValue;
		String[] conditionsNamesArr = paramsMap.get(HttpParams.Filter.ATTR_NAME);
		if (conditionsNamesArr == null) {
			return filter;
		}
		for (int i = 0; i < conditionsNamesArr.length; i++) {
			try {
				filtrationType = FiltrationType.getByTextRepr(paramsMap.get(HttpParams.Filter.FILTRATION_TYPE)[i]);
				attrName = paramsMap.get(HttpParams.Filter.ATTR_NAME)[i];
				attrValue = paramsMap.get(HttpParams.Filter.ATTR_VALUE)[i];
				if (attrName == null || attrValue == null) {
					throw new NullPointerException();
				}
				filter.addCondition(filtrationType, attrName, attrValue);
			} catch (RuntimeException e) {
				throw new InvalidRequestException(e);
			}
		}
		return filter;
	}

	@Override
	public List<Course> parseCourses(Map<String, String[]> paramsMap) {
		List<Course> coursesList = new ArrayList<>();
		String[] idsArr = paramsMap.get(Tables.Courses.Attr.COURSE_ID.getAttrName());
		if (idsArr == null) {
			return coursesList;
		}
		Course course;
		for (int i = 0; i < idsArr.length; i++) {
			course = new Course();

			try {
				course.setId(Integer.parseInt(paramsMap.get(Tables.Courses.Attr.COURSE_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				course.setName(paramsMap.get(Tables.Courses.Attr.NAME.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				course.setDuration(paramsMap.get(Tables.Courses.Attr.DURATION.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				course.setDescription(paramsMap.get(Tables.Courses.Attr.DESCRIPTION.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			coursesList.add(course);
		}

		return coursesList;

	}

	@Override
	public List<CoursePlan> parseCoursePlans(Map<String, String[]> paramsMap) {
		List<CoursePlan> coursePlansList = new ArrayList<>();
		String[] idsArr = paramsMap.get(Tables.CoursesPlans.Attr.ID.getAttrName());
		if (idsArr == null) {
			return coursePlansList;
		}
		CoursePlan coursePlan;
		for (int i = 0; i < idsArr.length; i++) {
			coursePlan = new CoursePlan();

			try {
				coursePlan.setId(Integer.parseInt(paramsMap.get(Tables.CoursesPlans.Attr.ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				coursePlan.setCourseId(
						Integer.parseInt(paramsMap.get(Tables.CoursesPlans.Attr.COURSE_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				coursePlan.setTrainerId(
						Integer.parseInt(paramsMap.get(Tables.CoursesPlans.Attr.TRAINER_USER_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				coursePlan.setStatus(CoursePlanStatus
						.getByVal(Integer.valueOf(paramsMap.get(Tables.CoursesPlans.Attr.STATUS_ID.getAttrName())[i])));
			} catch (RuntimeException e) {
			}

			try {
				coursePlan.setDescription(paramsMap.get(Tables.CoursesPlans.Attr.DESCRIPTION.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				coursePlan.setStartDate(
						LocalDate.parse(paramsMap.get(Tables.CoursesPlans.Attr.START_DATE.getAttrName())[i]));

			} catch (RuntimeException e) {
			}

			coursePlansList.add(coursePlan);
		}

		return coursePlansList;
	}

	@Override
	public List<Lesson> parseLessons(Map<String, String[]> paramsMap) {
		List<Lesson> lessonsList = new ArrayList<>();
		String[] idsArr = paramsMap.get(Tables.Lessons.Attr.ID.getAttrName());
		if (idsArr == null) {
			return lessonsList;
		}
		Lesson lesson;
		for (int i = 0; i < idsArr.length; i++) {
			lesson = new Lesson();

			try {
				lesson.setId(Integer.parseInt(paramsMap.get(Tables.Lessons.Attr.ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				lesson.setCoursePlanId(
						Integer.parseInt(paramsMap.get(Tables.Lessons.Attr.COURSE_PLAN_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				lesson.setStartTime(
						LocalDateTime.parse(paramsMap.get(Tables.Lessons.Attr.START_TIME.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				lesson.setDuration(Integer.valueOf(paramsMap.get(Tables.Lessons.Attr.DURATION.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				lesson.setClassroomNumber(
						Integer.valueOf(paramsMap.get(Tables.Lessons.Attr.CLASSROOM_NUMBER.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				lesson.setCompleted(
						Boolean.parseBoolean(paramsMap.get(Tables.Lessons.Attr.IS_COMPLETED.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				lesson.setRemarks(paramsMap.get(Tables.Lessons.Attr.REMARKS.getAttrName())[0]);
			} catch (RuntimeException e) {
			}

			lessonsList.add(lesson);
		}

		return lessonsList;

	}

	@Override
	public List<StudentsHaveCoursesPlans> parseStudentsHaveCoursesPlans(Map<String, String[]> paramsMap)
			throws InvalidRequestException {
		List<StudentsHaveCoursesPlans> studentsHaveCoursePlansList = new ArrayList<>();
		String[] idsArr = paramsMap.get(Tables.StudentsHaveCoursesPlans.Attr.ID.getAttrName());
		if (idsArr == null) {
			return studentsHaveCoursePlansList;
		}
		StudentsHaveCoursesPlans entity = new StudentsHaveCoursesPlans();
		for (int i = 0; i < idsArr.length; i++) {
			try {
				entity.setId(Integer.parseInt(paramsMap.get(Tables.StudentsHaveCoursesPlans.Attr.ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				entity.setCoursePlanId(Integer
						.parseInt(paramsMap.get(Tables.StudentsHaveCoursesPlans.Attr.COURSE_PLAN_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				entity.setStudentUserId(Integer.parseInt(
						paramsMap.get(Tables.StudentsHaveCoursesPlans.Attr.STUDENT_USER_ID.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			try {
				entity.setReview(paramsMap.get(Tables.StudentsHaveCoursesPlans.Attr.REVIEW.getAttrName())[i]);
			} catch (RuntimeException e) {
			}

			try {
				entity.setMark(
						Integer.parseInt(paramsMap.get(Tables.StudentsHaveCoursesPlans.Attr.MARK.getAttrName())[i]));
			} catch (RuntimeException e) {
			}

			studentsHaveCoursePlansList.add(entity);
		}

		return studentsHaveCoursePlansList;
	}

}
