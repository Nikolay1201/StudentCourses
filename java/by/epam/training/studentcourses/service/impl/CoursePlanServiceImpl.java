package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.util.entity.CoursePlan;

public class CoursePlanServiceImpl extends EntityCRUDAbstractService<CoursePlan> 
	implements CoursePlanService {
	
	public CoursePlanServiceImpl() {
		init(DAOFactory.getInstance().getCoursePlanDAO(), ValidatorFactory.getCoursePlanValidator());
	}
	
}
