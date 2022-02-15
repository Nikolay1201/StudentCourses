package by.epam.training.studentcourses.controller.impl.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.impl.dynamic.EntityParserImpl;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.User;

public class GotoCoursesPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoCoursesPageCommand.class);
	private static final CourseService courseService = service.getCourseService();
	private static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws InternalControllerException, InvalidRequestException, NotAllowedException {
		List<Course> courseList = null;
		User user = (User) request.getSession().getAttribute(ContextParams.Session.USER);
		request.setAttribute(ContextParams.Request.SELECT_MODE, request.getParameter(HttpParams.SELECT_MODE));
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			courseList = courseService.getByFilter(user, filter);
			request.setAttribute(ContextParams.Request.ENTITIES_LIST, courseList);
			return JspPaths.COURSES;
		} catch (by.epam.training.studentcourses.service.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		} catch (by.epam.training.studentcourses.service.exception.NotAllowedException e) {
			throw new NotAllowedException(e.getAllowedRolesList(), e);
		} catch (by.epam.training.studentcourses.service.exception.InternalServiceException e) {
			throw new InternalControllerException(e);
		}
	}

}
