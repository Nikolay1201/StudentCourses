package by.epam.training.studentcourses.service.impl.validation;

import java.util.ArrayList;
import java.util.List;

import by.epam.training.studentcourses.dao.DAOFactory;
import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.exception.InternalDAOException;
import by.epam.training.studentcourses.dao.exception.InvalidRequestException;
import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class CoursePlanValidatorImpl implements EntityValidator<CoursePlan> {

	private static UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

	@Override
	public List<TableAttr> validate(CoursePlan coursePlan, boolean skipNull)
			throws InternalServiceException {
		List<TableAttr> invalidAttrList = new ArrayList<>();
		if (coursePlan.getCourseId() == null && !skipNull) {
			invalidAttrList.add(Tables.CoursesPlans.Attr.COURSE_ID);
		}
		if (coursePlan.getStatus() == null && !skipNull) {
			invalidAttrList.add(Tables.CoursesPlans.Attr.STATUS_ID);
		}
		User trainer = null;

		try {
			trainer = userDAO.getById(coursePlan.getTrainerUserId());
			if ((coursePlan.getTrainerUserId() == null && !skipNull) || trainer.getRole() != UserRole.TRAINER) {
				invalidAttrList.add(Tables.CoursesPlans.Attr.TRAINER_USER_ID);
			}
		} catch (by.epam.training.studentcourses.dao.exception.NoSuchEntityException e) {
			invalidAttrList.add(Tables.CoursesPlans.Attr.TRAINER_USER_ID);
		} catch (InvalidRequestException | InternalDAOException e) {
			throw new InternalServiceException(e);
		}

		return invalidAttrList;
	}

}
