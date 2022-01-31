package by.epam.training.studentcourses.controller.impl.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;

public class GotoSandCPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoSandCPageCommand.class);
	private static final StudentsHaveCoursesPlansService service = ServiceFactory.getInstance()
			.getStudentsHaveCoursesPlansService();
	private static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws InternalControllerException {
		List<StudentsHaveCoursesPlans> entitiesList = null;
		User user = (User) request.getSession().getAttribute(ContextParams.Session.USER);
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			entitiesList = service.getByFilter(user, filter);
		} catch (ServiceException e) {
			throw new InternalControllerException(e);
		} catch (by.epam.training.studentcourses.controller.exception.InvalidRequestException e) {

		}
		request.setAttribute(ContextParams.Request.ENTITIES_LIST, entitiesList);
		return JspPaths.STUDENTS_AND_COURSES_PLANS;
	}

}
