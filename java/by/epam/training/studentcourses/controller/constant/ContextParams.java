package by.epam.training.studentcourses.controller.constant;

public class ContextParams {

	private static ContextParams instance = new ContextParams();

	public static ContextParams getInstance() {
		return instance;
	}

	public class Servlet {
		public static final String STD_ERROR_MESSAGE = "stderrmessage";
		public static final String APP_NAME = "appname";
		public static final String LOCALSTORAGE_PARAM_ENTITY_ID = "entityId";
		public static final String LOCALSTORAGE_PARAM_ENTITY_DESCR = "entityDescr";
	}

	public class Session {
		public static final String USER = "user";
		public static final String LOGIN = HttpParams.Authentification.LOGIN;
		public static final String LANG = HttpParams.LANG;
		public static final String SHOW_ONLY_MY_COURSES_PLANS = "onlyMyCoursesPlans";
		public static final String SHOW_ONLY_MY_LESSONS = "onlyMyLessons";
	}

	public class Request {
		public class SelectModeValues {
			public static final String USUAL = "usual";
			public static final String MODAL_WINDOW = "modalwin";
		}

		public static final String SELECT_MODE = "selectMode";
		public static final String ENTITIES_LIST = "entitiesList";
		public static final String STUDENT_COURSES_PLANS_LIST = "studentCoursePlansList";
		public static final String TEACHER_COURSE_PLANS_LIST = "teacherCoursePlansList";
		public static final String COURSES_NAMES_LIST = "coursesNamesList";
		public static final String TRAINERS_NAMES_LIST = "trainersNamesList";
		public static final String STUDENTS_NAMES_LIST = "studentsNamesList";
		public static final String COURSES_PLANS_NAMES_LIST = "coursesPlansNamesList";

		public static final String ERROR_MESSAGE = "errmessage";
	}


}
