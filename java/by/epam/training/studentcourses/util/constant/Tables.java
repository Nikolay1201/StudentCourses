package by.epam.training.studentcourses.util.constant;

import by.epam.training.studentcourses.util.TableAttr;

public class Tables {
	
	public static class Users {
		public static final String TABLE_NAME = "users";
		
		private Users() {}
		
		public enum Attr implements TableAttr {
			USER_ID("user_id"),
			ROLE_ID("role_id"),
			NAME("name"),
			SURENAME("surename"),
			PATRONYMIC("patronymic"),
			BIRTH_DATE("birth_date"),
			PHONE_NUMBER("phone_number"),
			LOGIN("login"),
			PASSWORD("password"),
			EMAIL("email"),
			DESCRIPTION("description"),
			REG_DATETIME("registration_timestamp");
			
			private String attrName;
	
			private Attr(String attrName) {
				this.attrName = attrName;
			}
	
			@Override
			public String getAttrName() {
				return attrName;
			}
	
		}
	}
	
	public static class Courses {
		public static final String TABLE_NAME = "courses";
		
		private Courses() {}
		
		public enum Attr implements TableAttr {
			COURSE_ID("course_id"),
			NAME("name"),
			DURATION("duration"),
			DESCRIPTION("description"),
			CREATION_TIMESTAMP("creation_timestamp");
			
			private String attrName;
	
			private Attr(String attrName) {
				this.attrName = attrName;
			}
	
			public String getAttrName() {
				return attrName;
			}
			
		}
	}
	
	public static class CoursesPlans {
		public static final String TABLE_NAME = "courses_plans";
		
		private CoursesPlans() {}
		
		public enum Attr implements TableAttr {
			COURSE_PLAN_ID("course_plan_id"),
			COURSE_ID("course_id"),
			TRAINER_USER_ID("trainer_user_id"),
			STATUS_ID("status_id"),
			DESCRIPTION("descripition");
			
			private String attrName;
	
			private Attr(String attrName) {
				this.attrName = attrName;
			}
	
			public String getAttrName() {
				return attrName;
			}

		}
	}
	
	public static class Lessons {
		public static final String TABLE_NAME = "lessons";
		
		private Lessons() {}
		
		public enum Attr implements TableAttr {
			LESSON_ID("lesson_id"),
			COURSE_PLAN_ID("courses_plan"),
			START_TIME("start_time"),
			DURATION("duration"),
			CLASSROOM_NUMBER("classroom_number"),
			IS_COMPLETED("is_completed"),
			REMARKS("remarks");
			
			private String attrName;
	
			private Attr(String attrName) {
				this.attrName = attrName;
			}
	
			public String getAttrName() {
				return attrName;
			}
		}
	}
	
	public static class UserSessionToken {
		public static final String TABLE_NAME = "session_tokens";
		
		private UserSessionToken() {}
		
		public enum Attr implements TableAttr {
			SESSION_TOKEN_ID("session_token_id"),
			USER_ID("user_id"),
			SESSION_TOKEN("session_token");
			
			private String attrName;
	
			private Attr(String attrName) {
				this.attrName = attrName;
			}
	
			public String getAttrName() {
				return attrName;
			}
		}
	}
	
	private Tables() {}		
	
}
