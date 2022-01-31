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
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class GotoCoursesPlansPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoCoursesPlansPageCommand.class);
	private static final CoursePlanService coursePlanService = ServiceFactory.getInstance().getCoursePlanService();
	private static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws InternalControllerException {
		List<CoursePlan> coursesPlansList = null;
		List<CoursePlan> myCoursesPlansList = null;
		User user = (User)request.getSession().getAttribute(ContextParams.Session.USER);
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			coursesPlansList = coursePlanService.getByFilter(user, filter);
			if (user.getRole() == UserRole.STUDENT) {
				myCoursesPlansList = coursePlanService.getCoursesPlansOfUser(user, user);
			}
		} catch (ServiceException e) {
			throw new InternalControllerException(e);
		} catch (by.epam.training.studentcourses.controller.exception.InvalidRequestException e) {
			
		}
		request.setAttribute(ContextParams.Request.ENTITIES_LIST, coursesPlansList);
		request.setAttribute(ContextParams.Request.MY_COURSES_PLANS_LIST, coursesPlansList);
		return JspPaths.COURSES_PLANS;
	}

}
