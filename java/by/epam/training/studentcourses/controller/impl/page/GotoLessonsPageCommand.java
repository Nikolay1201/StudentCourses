package by.epam.training.studentcourses.controller.impl.page;

import java.util.ArrayList;
import java.util.Collections;
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
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.exception.NotAuthorizedException;
import by.epam.training.studentcourses.controller.impl.dynamic.EntityParserImpl;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.service.LessonService;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;

public class GotoLessonsPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoLessonsPageCommand.class);
	private static final LessonService lessonService = service.getLessonService();
	private static final CoursePlanService coursePlanService = service.getCoursePlanService();
	private static final CourseService courseService = service.getCourseService();
	private static final EntityParser parser = EntityParserImpl.getInstance();
	private static final List<UserRole> allowedRolesList = new ArrayList<>();
	
	static {
		Collections.addAll(allowedRolesList, UserRole.values());
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
		List<Lesson> lessonsList = null;
		List<String> coursesPlansNamesList = null;
		User user = (User) request.getSession().getAttribute(ContextParams.Session.USER);
		if (user == null) {
			throw new NotAuthorizedException();
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
			switch (user.getRole()) {
				case STUDENT:
					lessonsList = lessonService.getStudentLessons(user, user, filter);
					break;
				case TRAINER:
					if (request.getSession().getAttribute(ContextParams.Session.SHOW_ONLY_MY_LESSONS) != null) {
						lessonsList = lessonService.getTrainerLessons(user, user, filter);
					} else {
						lessonsList = lessonService.getByFilter(user, filter);
					}
					break;
				default:
					throw new NotAllowedException(allowedRolesList);
			}
			coursesPlansNamesList = new LinkedList<>();
			CoursePlan coursePlan;
			Course course;
			for (int i = 0; i < lessonsList.size(); i++) {
				try {
					coursePlan = coursePlanService.getById(user, lessonsList.get(i).getCoursePlanId());
					course = courseService.getById(user, coursePlan.getCourseId());
				} catch (NoSuchEntityException e) { 
					throw new InvalidRequestException(e);
				}
				coursesPlansNamesList.add(course.getName());
			}
			request.setAttribute(ContextParams.Request.ENTITIES_LIST, lessonsList);
			request.setAttribute(ContextParams.Request.COURSES_PLANS_NAMES_LIST, coursesPlansNamesList);
			return JspPaths.LESSONS;
		} catch (InternalServiceException e) {
			throw new InternalControllerException(e);
		} catch (by.epam.training.studentcourses.service.exception.NotAllowedException e) {
			throw new NotAllowedException(e.getAllowedRolesList(), e);
		} catch (by.epam.training.studentcourses.service.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		} 


	}

}
