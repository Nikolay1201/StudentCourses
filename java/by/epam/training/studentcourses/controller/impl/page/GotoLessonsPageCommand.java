package by.epam.training.studentcourses.controller.impl.page;

import java.util.LinkedList;
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
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.service.LessonService;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.User;

public class GotoLessonsPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoLessonsPageCommand.class);
	private static final LessonService lessonService = ServiceFactory.getInstance().getLessonService();
	private static final CoursePlanService coursePlanService = ServiceFactory.getInstance().getCoursePlanService();
	private static final CourseService courseService = ServiceFactory.getInstance().getCourseService();
	private static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
		List<Lesson> lessonsList = null;
		List<String> coursesPlansNamesList = null;
		User user = (User) request.getSession().getAttribute(ContextParams.Session.USER);
		if (user == null) {
			throw new NotAllowedException();
		}
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			String showOnlyMyLessons = request.getParameter(HttpParams.SHOW_ONLY_MINE);

			if (showOnlyMyLessons != null) {
				if (showOnlyMyLessons.equals(Boolean.TRUE.toString())) {
					request.getSession().setAttribute(ContextParams.Session.SHOW_ONLY_MY_LESSONS,
							Boolean.TRUE.toString());
				}
				if (showOnlyMyLessons.equals(Boolean.FALSE.toString())) {
					request.getSession().removeAttribute(ContextParams.Session.SHOW_ONLY_MY_LESSONS);
				}
			}

			if (request.getSession().getAttribute(ContextParams.Session.SHOW_ONLY_MY_LESSONS) != null) {
				switch (user.getRole()) {
				case STUDENT:
					lessonsList = lessonService.getStudentLessons(user, user, filter);
					break;
				case TRAINER:
					lessonsList = lessonService.getTrainerLessons(user, user, filter);
					break;
				default:
					throw new NotAllowedException();
				}
			} else {
				lessonsList = lessonService.getByFilter(user, filter);
			}

			coursesPlansNamesList = new LinkedList<>();
			CoursePlan coursePlan;
			Course course;
			for (int i = 0; i < lessonsList.size(); i++) {
				coursePlan = coursePlanService.getById(user, lessonsList.get(i).getCoursePlanId());
				course = courseService.getById(user, coursePlan.getCourseId());
				coursesPlansNamesList.add(course.getName());
			}
		} catch (ServiceException e) {
			throw new ControllerException(e);
		} catch (by.epam.training.studentcourses.controller.exception.InvalidRequestException e) {
			log.debug(e);
		}

		request.setAttribute(ContextParams.Request.ENTITIES_LIST, lessonsList);
		request.setAttribute(ContextParams.Request.COURSES_PLANS_NAMES_LIST, coursesPlansNamesList);
		return JspPaths.LESSONS;

	}

}
