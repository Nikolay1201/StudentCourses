package by.epam.training.studentcourses.service.impl;

import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.service.impl.validation.SystemUser;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class StudentsHaveCoursesPlansServiceImpl extends EntityCRUDAbstractService<StudentsHaveCoursesPlans>
		implements StudentsHaveCoursesPlansService {

	private static CRUDAuthorizator<StudentsHaveCoursesPlans> studentsHaveCPauthorizator = new CRUDAuthorizator<StudentsHaveCoursesPlans>() {

		@Override
		public void update(User user, StudentsHaveCoursesPlans entity)
				throws NotAllowedException, InternalServiceException, NoSuchEntityException {
			if (user.getRole() == UserRole.STUDENT) {
				try {
					Filter filter = new Filter();
					filter.addCondition(Tables.StudentsHaveCoursesPlans.Attr.COURSE_PLAN_ID, entity.getCoursePlanId());
					filter.addCondition(Tables.StudentsHaveCoursesPlans.Attr.STUDENT_USER_ID, user.getId());
					List<StudentsHaveCoursesPlans> studentCoursesList = instance.getByFilter(SystemUser.getSystem(),
							filter);
					if (!studentCoursesList.isEmpty()) {
						throw new NotAllowedException();
					}
				} catch (InvalidRequestException e) {
					throw new InternalServiceException(e);
				}
			}
			super.update(user, entity);
		}

	};

	private static StudentsHaveCoursesPlansService instance = new StudentsHaveCoursesPlansServiceImpl();

	public static StudentsHaveCoursesPlansService getInstance() {
		return instance;
	}

	private StudentsHaveCoursesPlansServiceImpl() {
		init(DAOFactory.getInstance().getStudentsHaveCoursesPlansDAO(),
				ValidatorFactory.getStudentsHaveCoursesPlansValidator(), studentsHaveCPauthorizator);
	}

}
