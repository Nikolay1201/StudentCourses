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
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.impl.dynamic.EntityParserImpl;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.User;

public class GotoCoursesPlansPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoCoursesPlansPageCommand.class);
	private static final CoursePlanService coursePlanService = service.getCoursePlanService();
	private static final UserService userSerivce = service.getUserService();
	private static final CourseService courseService = service.getCourseService();
	private static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
		List<CoursePlan> coursesPlansList = null;
		List<String> coursesNamesList = null;
		List<String> trainersNamesList = null;
		User user = (User) request.getSession().getAttribute(ContextParams.Session.USER);
		request.setAttribute(ContextParams.Request.SELECT_MODE, request.getParameter(HttpParams.SELECT_MODE));
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			String showOnlyMyCoursesPlans = request.getParameter(HttpParams.SHOW_ONLY_MINE);
			if (showOnlyMyCoursesPlans != null) {
				if (showOnlyMyCoursesPlans.equals(Boolean.TRUE.toString())) {
					request.getSession().setAttribute(ContextParams.Session.SHOW_ONLY_MY_COURSES_PLANS,
							Boolean.TRUE.toString());
				}
				if (showOnlyMyCoursesPlans.equals(Boolean.FALSE.toString())) {
					request.getSession().removeAttribute(ContextParams.Session.SHOW_ONLY_MY_COURSES_PLANS);
				}
			}

			if (request.getSession().getAttribute(ContextParams.Session.SHOW_ONLY_MY_COURSES_PLANS) != null
					&& user != null) {
				switch (user.getRole()) {
				case STUDENT:
					coursesPlansList = coursePlanService.getStudentCoursePlans(user, user, filter);
					break;
				case TRAINER:
					coursesPlansList = coursePlanService.getTrainerCoursePlans(user, user, filter);
					break;
				default:
					coursesPlansList = coursePlanService.getByFilter(user, filter);
				}
			} else {
				coursesPlansList = coursePlanService.getByFilter(user, filter);
			}
			Course course;
			User trainer;
			coursesNamesList = new LinkedList<>();
			trainersNamesList = new LinkedList<>();
			try {
				for (int i = 0; i < coursesPlansList.size(); i++) {
					course = courseService.getById(user, coursesPlansList.get(i).getCourseId());
					coursesNamesList.add(course.getName());
					trainer = userSerivce.getByFilter(user, new Filter(Tables.Users.Attr.USER_ID,
							coursesPlansList.get(i).getTrainerUserId().toString())).get(0);
					trainersNamesList.add(String.format("%s %s %s", trainer.getSurename(), trainer.getName(),
							trainer.getPatronymic()));
				}
			} catch (NoSuchEntityException e) {
				throw new InvalidRequestException(e);
			}
			request.setAttribute(ContextParams.Request.ENTITIES_LIST, coursesPlansList);
			request.setAttribute(ContextParams.Request.STUDENT_COURSES_PLANS_LIST, coursesPlansList);
			request.setAttribute(ContextParams.Request.COURSES_NAMES_LIST, coursesNamesList);
			request.setAttribute(ContextParams.Request.TRAINERS_NAMES_LIST, trainersNamesList);
			return JspPaths.COURSES_PLANS;
		} catch (by.epam.training.studentcourses.service.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		} catch (by.epam.training.studentcourses.service.exception.NotAllowedException e) {
			throw new NotAllowedException(e.getAllowedRolesList(), e);
		} catch (by.epam.training.studentcourses.service.exception.InternalServiceException e) {
			throw new InternalControllerException(e);
		}

	}

}
