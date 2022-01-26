package by.epam.training.studentcourses.controller.constant;

public class RequestParams {

	public class OperationTypes {
		public static final String AUTHENTICATION = "auth";
		public static final String ADD = "add";
		public static final String GET = "get";
		public static final String UPDATE = "upd";
		public static final String DELETE = "del";
	}
	
	public class Filter {
		public static final String ATTR_NAME = "param";
		public static final String FILTRATION_TYPE = "ftype";
		public static final String ATTR_VALUE = "val";
	}
	
	public class Authentification {
		public static final String LOGIN = "login";
		public static final String PASSWORD = "password";
	}
	
	public static final String OPERATION_TYPE = "optype";
	public static final String ENTITY_TYPE = "entity";
	
	
}
