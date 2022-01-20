package by.epam.training.studentcourses.service.impl;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.service.ValidatorFactory;
import by.epam.training.studentcourses.util.entity.Lesson;

public class LessonServiceImpl extends EntityCRUDAbstractService<Lesson> {
	
	public LessonServiceImpl() {
		super(DAOFactory.getInstance().getLessonDAO(), ValidatorFactory.getLessonValidator());
	}
	
}
