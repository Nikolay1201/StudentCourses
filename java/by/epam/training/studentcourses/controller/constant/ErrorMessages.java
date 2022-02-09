package by.epam.training.studentcourses.controller.constant;

public class ErrorMessages {
	
	public class Authorization {
		public static final String INVALID_LOGIN = 
				"Пользователя с данным логином не существует";
		public static final String INVALID_PASSWORD = 
				"Неверный пароль";
		public static final String NOT_ALLOWED = 
				"Недостаточно прав";
	}
	
	public class EntityCRUD {
		public static final String INVALID_PARAMETERS = 
				"Свойства заданы неверно, повторите попытку";
	}
	
	public static final String INTERNAL_ERROR = 
			"Внутренняя ошибка\nОбратитесь в техподдержку";
	public static final String NO_SUCH_OPERATION = 
			"Недопустимая операция";
	public static final String INVALID_REQUEST = 
			"Недопустимый запрос";
	public static final String PAGE_NOT_FOUND = 
			"Страницы с данным ардесом не существует";
	
}
