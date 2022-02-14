package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class StudentsHaveCoursesPlansServiceImpl extends EntityCRUDAbstractService<StudentsHaveCoursesPlans>
		implements StudentsHaveCoursesPlansService {

	private static StudentsHaveCoursesPlansService instance = new StudentsHaveCoursesPlansServiceImpl();
	private static CRUDAuthorizator<StudentsHaveCoursesPlans> authorizator = 
			new CRUDAuthorizator<>(UserRole.ADMIN, UserRole.TRAINER) {

		@Override
		public void update(User user, StudentsHaveCoursesPlans entity)
				throws NotAllowedException, InternalServiceException, NoSuchEntityException {
			if (user.getRole() == UserRole.STUDENT) {
				
			}
			super.update(user, entity);
		}

	};

	public static StudentsHaveCoursesPlansService getInstance() {
		return instance;
	}

	private StudentsHaveCoursesPlansServiceImpl() {
		init(DAOFactory.getInstance().getStudentsHaveCoursesPlansDAO(),
				ValidatorFactory.getStudentsHaveCoursesPlansValidator(),
				authorizator);
	}

}
