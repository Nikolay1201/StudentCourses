package by.epam.training.studentcourses.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.constant.DataAccessCommands;
import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.impl.dynamic.AddEntityCommand;
import by.epam.training.studentcourses.controller.impl.dynamic.AuthenticationCommand;
import by.epam.training.studentcourses.controller.impl.dynamic.ChangeLocaleCommand;
import by.epam.training.studentcourses.controller.impl.dynamic.DeleteEntityByIdCommand;
import by.epam.training.studentcourses.controller.impl.dynamic.GetFileCommand;
import by.epam.training.studentcourses.controller.impl.dynamic.UpdateEntityCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoCoursesPageCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoCoursesPlansPageCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoIndexPageCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoLoginPageCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoProfilePageCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoSandCPageCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoTestPageCommand;
import by.epam.training.studentcourses.controller.impl.page.GotoUsersPageCommand;
import by.epam.training.studentcourses.controller.impl.page.LogoutCommand;
import by.epam.training.studentcourses.service.Service;
import by.epam.training.studentcourses.service.ServiceFactory;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;
import by.epam.training.studentcourses.util.entity.User;

public class Invoker {
	private static Logger log = LogManager.getLogger(Invoker.class);
	private static final Map<String, Command> commandMap = new HashMap<>();
	private static Service service = ServiceFactory.getInstance();

	public static void init() {
		// pages
		commandMap.put("GET/page" + null, new GotoIndexPageCommand());
		commandMap.put("GET/page" + "", new GotoIndexPageCommand());
		commandMap.put("GET/page" + "/", new GotoIndexPageCommand());
		commandMap.put("GET/page" + JspPaths.TEST, new GotoTestPageCommand());
		commandMap.put("GET/page" + JspPaths.INDEX, new GotoIndexPageCommand());
		commandMap.put("GET/page" + JspPaths.LOGIN, new GotoLoginPageCommand());
		commandMap.put("GET/page" + JspPaths.PROFILE, new GotoProfilePageCommand());
		commandMap.put("GET/page" + JspPaths.USERS, new GotoUsersPageCommand());
		commandMap.put("GET/page" + JspPaths.LOGOUT, new LogoutCommand());
		commandMap.put("GET/page" + JspPaths.COURSES, new GotoCoursesPageCommand());
		commandMap.put("GET/page" + JspPaths.COURSES_PLANS, new GotoCoursesPlansPageCommand());
		commandMap.put("GET/page" + JspPaths.STUDENTS_AND_COURSES_PLANS, new GotoSandCPageCommand());

		// dynamic
		commandMap.put("GET/data" + DataAccessCommands.AUTH, new AuthenticationCommand());
		commandMap.put("GET/data" + DataAccessCommands.CHANGE_LOCALE, new ChangeLocaleCommand());
		commandMap.put("POST/data/file", new GetFileCommand());

		commandMap.put("GET/data" + DataAccessCommands.ADD_USER, new AddEntityCommand<User>(service.getUserService()) {
			@Override
			public List<User> parseEntities(Map paramsMap) throws InvalidRequestException {
				return parser.parseUsers(paramsMap);
			}
		});
		commandMap.put("GET/data" + DataAccessCommands.UPDATE_USER,
				new UpdateEntityCommand<User>(service.getUserService()) {
					@Override
					public List<User> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseUsers(paramsMap);
					}
				});
		commandMap.put("GET/data" + DataAccessCommands.DELETE_USER,
				new DeleteEntityByIdCommand<User>(service.getUserService()) {

					@Override
					protected List<User> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseUsers(paramsMap);
					}

				});

		commandMap.put("GET/data" + DataAccessCommands.ADD_COURSE,
				new AddEntityCommand<Course>(service.getCourseService()) {
					@Override
					public List<Course> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseCourses(paramsMap);
					}
				});
		commandMap.put("GET/data" + DataAccessCommands.UPDATE_COURSE,
				new UpdateEntityCommand<Course>(service.getCourseService()) {
					@Override
					public List<Course> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseCourses(paramsMap);
					}
				});
		commandMap.put("GET/data" + DataAccessCommands.DELETE_COURSE,
				new DeleteEntityByIdCommand<Course>(service.getCourseService()) {

					@Override
					protected List<Course> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseCourses(paramsMap);
					}
				});
		
		commandMap.put("GET/data" + DataAccessCommands.ADD_COURSE_PLAN,
				new AddEntityCommand<CoursePlan>(service.getCoursePlanService()) {
					@Override
					public List<CoursePlan> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseCoursePlans(paramsMap);
					}
				});
		commandMap.put("GET/data" + DataAccessCommands.UPDATE_COURSE_PLAN,
				new UpdateEntityCommand<CoursePlan>(service.getCoursePlanService()) {
					@Override
					public List<CoursePlan> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseCoursePlans(paramsMap);
					}
				});
		commandMap.put("GET/data" + DataAccessCommands.DELETE_COURSE_PLAN,
				new DeleteEntityByIdCommand<CoursePlan>(service.getCoursePlanService()) {

					@Override
					protected List<CoursePlan> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseCoursePlans(paramsMap);
					}
				});
		
		commandMap.put("GET/data" + DataAccessCommands.ADD_USER_TO_COURSEPLAN,
				new AddEntityCommand<StudentsHaveCoursesPlans>(service.getStudentsHaveCoursesPlansService()) {
					@Override
					public List<StudentsHaveCoursesPlans> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseStudentsHaveCoursesPlans(paramsMap);
					}
				});
		commandMap.put("GET/data" + DataAccessCommands.UPDATE_USER_TO_COURSEPLAN,
				new UpdateEntityCommand<StudentsHaveCoursesPlans>(service.getStudentsHaveCoursesPlansService()) {
					@Override
					public List<StudentsHaveCoursesPlans> parseEntities(Map paramsMap) throws InvalidRequestException {
						return parser.parseStudentsHaveCoursesPlans(paramsMap);
					}
				});
		commandMap.put("GET/data" + DataAccessCommands.DELETE_USER_OF_COURSEPLAN,
				new DeleteEntityByIdCommand<StudentsHaveCoursesPlans>(service.getStudentsHaveCoursesPlansService()) {

					@Override
					protected List<StudentsHaveCoursesPlans> parseEntities(Map paramsMap)
							throws InvalidRequestException {
						return parser.parseStudentsHaveCoursesPlans(paramsMap);
					}
				});

	}

	public static String execute(String commandName, HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		log.trace("command: {}", commandName);
		Command command = commandMap.get(commandName);
		if (command == null) {
			throw new InvalidRequestException(ErrorMessages.PAGE_NOT_FOUND);
		}
		return command.execute(request, response);
	}

	private Invoker() {
	}
}
