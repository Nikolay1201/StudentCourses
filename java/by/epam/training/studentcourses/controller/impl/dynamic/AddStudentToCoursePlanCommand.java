package by.epam.training.studentcourses.controller.impl.dynamic;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.EntityCRUDService;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidEntitiesException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;

public abstract class AddStudentToCoursePlanCommand implements Command {

	private static Logger log = LogManager.getLogger(AddStudentToCoursePlanCommand.class);
	private static final StudentsHaveCoursesPlansService service = ServiceFactory.getInstance()
			.getStudentsHaveCoursesPlansService();
	protected static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		Integer courseId;
		Integer userId;
		try {
			courseId = Integer.parseInt(request.getParameter(Tables.Courses.Attr.COURSE_ID.getAttrName()));
			userId = Integer.parseInt(request.getParameter(Tables.Users.Attr.USER_ID.getAttrName()));
		} catch (NumberFormatException e) {
			throw new InvalidRequestException(e);
		}
		try {
			service.add((User) request.getSession().getAttribute(ContextParams.Session.USER), entitiesList);
		} catch (InvalidRequestException e) {
			StringBuilder errorMessage = new StringBuilder(ErrorMessages.EntityCRUD.INVALID_PARAMETERS);
			errorMessage.append("<br>");
			if (!e.getInvalidAttrsLists().isEmpty()) {
				for (TableAttr attr : e.getInvalidAttrsLists().get(0)) {
					errorMessage.append("- ");
					errorMessage.append(attr.getAttrName());
					errorMessage.append("<br>");
				}
			}
			response.setStatus(HttpParams.StatusCode.SC_UNPROCESSABLE_ENITIY);
			request.setAttribute(ContextParams.Request.ERROR_MESSAGE, errorMessage);
			return JspPaths.ERROR;
		} catch (NotAllowedException e) {
			throw new by.epam.training.studentcourses.controller.exception.NotAllowedException(e);
		} catch (InternalServiceException e) {
			throw new InternalControllerException(e);
		}	
		return null;
	}

}
