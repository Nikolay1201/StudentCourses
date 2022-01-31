package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.UserRole;

public class StudentsHaveCoursesPlansServiceImpl extends EntityCRUDAbstractService<StudentsHaveCoursesPlans>
		implements StudentsHaveCoursesPlansService {

	public StudentsHaveCoursesPlansServiceImpl() {
		init(DAOFactory.getInstance().getStudentsHaveCoursesPlansDAO(),
				ValidatorFactory.getStudentsHaveCoursesPlansValidator(),
				new CRUDAuthorizator<>(UserRole.ADMIN, UserRole.TRAINER));
	}

}
