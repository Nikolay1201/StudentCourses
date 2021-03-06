package by.epam.training.studentcourses.controller.impl.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.CoursePlanService;
import by.epam.training.studentcourses.service.CourseService;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.service.StudentsHaveCoursesPlansService;
import by.epam.training.studentcourses.service.UserService;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;

public class GotoSandCPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GotoSandCPageCommand.class);
	private static final StudentsHaveCoursesPlansService service = ServiceFactory.getInstance()
			.getStudentsHaveCoursesPlansService();
	private static final CoursePlanService coursePlanService = ServiceFactory.getInstance().getCoursePlanService();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final CourseService courseService = ServiceFactory.getInstance().getCourseService();
	private static final EntityParser parser = EntityParserImpl.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
		List<StudentsHaveCoursesPlans> entitiesList = null;
		List<String> coursesPlansNamesList = null;
		List<String> studentsNamesList = null;
		List<String> trainersNamesList = null;
		User user = (User) request.getSession().getAttribute(ContextParams.Session.USER);
		if (user == null) {
			throw new NotAllowedException();
		}
		try {
			Filter filter = parser.parseFilter(request.getParameterMap());
			log.debug(filter);
			entitiesList = service.getByFilter(user, filter);
			coursesPlansNamesList = new ArrayList<>();
			studentsNamesList = new ArrayList<>();
			trainersNamesList = new ArrayList<>();
			CoursePlan coursePlan;
			Course course;
			User student, trainer;
			for (int i = 0; i < entitiesList.size(); i ++) {
				coursePlan = coursePlanService.getById(user, entitiesList.get(i).getCoursePlanId());
				course = courseService.getById(user, coursePlan.getCourseId());
				coursesPlansNamesList.add(course.getName());
				trainer = userService.getByFilter(user,
						new Filter(Tables.Users.Attr.USER_ID, coursePlan.getTrainerUserId().toString())).get(0);
				trainersNamesList.add(String.format("%s %s %s", 
						trainer.getSurename(), trainer.getName(), trainer.getPatronymic()));
				student = userService.getByFilter(user,
						new Filter(Tables.Users.Attr.USER_ID, entitiesList.get(i).getStudentUserId().toString())).get(0);
				studentsNamesList.add(String.format("%s %s %s", 
						student.getSurename(), student.getName(), student.getPatronymic()));
				
			}
		} catch (ServiceException e) {
			throw new InternalControllerException(e);
		} catch (by.epam.training.studentcourses.controller.exception.InvalidRequestException e) {

		}
		request.setAttribute(ContextParams.Request.ENTITIES_LIST, entitiesList);
		request.setAttribute(ContextParams.Request.COURSES_PLANS_NAMES_LIST, coursesPlansNamesList);
		request.setAttribute(ContextParams.Request.STUDENTS_NAMES_LIST, studentsNamesList);
		return JspPaths.STUDENTS_AND_COURSES_PLANS;
	}

}
