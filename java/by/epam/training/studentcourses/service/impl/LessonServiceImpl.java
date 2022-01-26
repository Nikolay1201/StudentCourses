package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.UserRole;

public class LessonServiceImpl extends EntityCRUDAbstractService<Lesson> {
	
	public LessonServiceImpl() {
		init(DAOFactory.getInstance().getLessonDAO(), ValidatorFactory.getLessonValidator(),
				new CRUDAuthorizator<>(UserRole.ADMIN, UserRole.TRAINER));
	}
	
}
