package by.epam.training.studentcourses.service.impl;

import java.util.LinkedList;
import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class CoursePlanServiceImpl extends EntityCRUDAbstractService<CoursePlan> implements CoursePlanService {

	private static CoursePlanService instance = new CoursePlanServiceImpl();
	private static StudentsHaveCoursesPlansService studentsHaveCoursesPlansService = StudentsHaveCoursesPlansServiceImpl
			.getInstance();
	private static CRUDAuthorizator<CoursePlan> coursesPlansAuthorizator = 
			new CRUDAuthorizator<>(UserRole.ADMIN, UserRole.TRAINER) {

		@Override
		public void getByFilter(User user, Filter filter) {
		}

		@Override
		public void getById(User user, Integer id) {
		}

	};

	public static CoursePlanService getInstance() {
		return instance;
	}

	private CoursePlanServiceImpl() {
		init(DAOFactory.getInstance().getCoursePlanDAO(), ValidatorFactory.getCoursePlanValidator(),
				coursesPlansAuthorizator);

	}

	@Override
	public List<CoursePlan> getStudentCoursePlans(User user, User student, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException {
		List<StudentsHaveCoursesPlans> list = studentsHaveCoursesPlansService.getByFilter(user,
				new Filter(Tables.StudentsHaveCoursesPlans.Attr.STUDENT_USER_ID, student.getId().toString()));
		List<CoursePlan> coursePlansList = new LinkedList<>();
		Filter newFilter;
		for (StudentsHaveCoursesPlans s : list) {
			newFilter = filter.clone();
			newFilter.addCondition(Tables.CoursesPlans.Attr.ID, s.getCoursePlanId());
			coursePlansList.addAll(getByFilter(user, newFilter));
		}
		return coursePlansList;
	}

	@Override
	public List<CoursePlan> getTrainerCoursePlans(User user, User trainer, Filter filter)
			throws InternalServiceException, NotAllowedException, InvalidRequestException {
		filter.addCondition(Tables.CoursesPlans.Attr.TRAINER_USER_ID, trainer.getId());
		return getByFilter(user, filter);
	}

}
