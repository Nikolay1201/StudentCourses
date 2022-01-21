package by.epam.training.studentcourses.controller.constant;

public class URLRequestParams {

	public class OperationType {
		public static final String ADD = "add";
		public static final String GET = "get";
		public static final String UPDATE = "upd";
		public static final String DELETE = "del";
		
		private OperationType() {}
	}
	
	public class Filter {
		public static final String ATTR_NAME = "param";
		public static final String FILTRATION_TYPE = "ftype";
		public static final String ATTR_VALUE = "val";
		
		private Filter() {}
	}
	
	public static final String OPERATION_TYPE = "type";
	public static final String ENTITY_TYPE = "entity";
	
	private URLRequestParams() {}
	
}
