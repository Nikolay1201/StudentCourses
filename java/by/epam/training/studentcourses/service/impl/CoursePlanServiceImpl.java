package by.epam.training.studentcourses.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class CoursePlanServiceImpl extends EntityCRUDAbstractService<CoursePlan> 
	implements CoursePlanService {
	
	public CoursePlanServiceImpl() {
		init(DAOFactory.getInstance().getCoursePlanDAO(), ValidatorFactory.getCoursePlanValidator(),
				new CRUDAuthorizator<>(UserRole.ADMIN, UserRole.TRAINER));
		
	}

	@Override
	public List<CoursePlan> getCoursesPlansOfUser(User user, User userRequired) {
		return new ArrayList<CoursePlan>();
	}
	
}
