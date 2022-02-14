package by.epam.training.studentcourses.service.impl;

import java.util.LinkedList;
import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.LessonDAO;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.LessonService;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.service.impl.validation.SystemUser;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class LessonServiceImpl extends EntityCRUDAbstractService<Lesson> implements LessonService {

	private static CRUDAuthorizator<Lesson> lessonAuthorizator = 
			new CRUDAuthorizator<Lesson>(UserRole.ADMIN, UserRole.TRAINER) {

		@Override
		public void update(User user, Lesson lesson)
				throws NotAllowedException, InternalServiceException, NoSuchEntityException {
			super.update(user, lesson);
			if (user.getRole() == UserRole.TRAINER) {
				try {
					CoursePlan coursePlan = coursePlanService.getById(SystemUser.getSystem(), lesson.getCoursePlanId());
					if (!user.getId().equals(coursePlan.getTrainerUserId())) {
						throw new NotAllowedException();
					}
				} catch (InvalidRequestException e) {
					throw new InternalServiceException(e);
				}

			}
		}

	};

	private static CoursePlanService coursePlanService = CoursePlanServiceImpl.getInstance();
	private static StudentsHaveCoursesPlansService studentsHaveCPService = 
			StudentsHaveCoursesPlansServiceImpl.getInstance();
	private static LessonDAO lessonDAO = DAOFactory.getInstance().getLessonDAO();
	private static LessonService instance = new LessonServiceImpl();

	public static LessonService getInstance() {
		return instance;
	}

	private LessonServiceImpl() {
		init(lessonDAO, ValidatorFactory.getLessonValidator(), lessonAuthorizator);

	}

	public List<Lesson> getTrainerLessons(User user, User teacher, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException {
		List<CoursePlan> coursesPlansList = coursePlanService.getByFilter(user,
				new Filter(Tables.CoursesPlans.Attr.TRAINER_USER_ID, teacher.getId()));
		Filter newFilter;
		List<Lesson> list = new LinkedList<>();
		for (CoursePlan coursePlan : coursesPlansList) {
			newFilter = filter.clone();
			newFilter.addCondition(Tables.Lessons.Attr.COURSE_PLAN_ID, coursePlan.getId());
			list.addAll(getByFilter(user, filter));
		}
		return list;
	}

	public List<Lesson> getStudentLessons(User user, User student, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException {
		List<StudentsHaveCoursesPlans> listOfStudentCoursePlans = studentsHaveCPService.getByFilter(user,
				new Filter(Tables.StudentsHaveCoursesPlans.Attr.STUDENT_USER_ID, student.getId()));
		List<Lesson> list = new LinkedList<>();
		Filter newFilter;
		for (StudentsHaveCoursesPlans studentHaveCP : listOfStudentCoursePlans) {
			newFilter = filter.clone();
			newFilter.addCondition(Tables.Lessons.Attr.COURSE_PLAN_ID, studentHaveCP.getCoursePlanId());
			list.addAll(getByFilter(user, newFilter));
		}
		return list;

	}

}
