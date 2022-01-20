package by.epam.training.studentcourses.dao.exception;

import by.epam.training.studentcourses.util.Filter;

public class DBErrorMessages {
		
	public static String getInvalidTableAttributeMessage(
		String tableName, String attrName) { 
		return String.format("Error in code: There is no attribute `%s` in the table `%s`",
				attrName, tableName);
	}
	
	public static String getTableNotExistsMessage(String tableName) { 
		return String.format("Error in code: Table `%s` does not exists", tableName);
	}
	
	public static String getFilterDoesntMatchTableMessage(String tableName, Filter filter) {
		return String.format("The following filter doesn't matches the table `%s`\n",
				tableName, filter.getStringRepr());
	}
	
	public static String getEntityDoesntContainIdMessage(Object entity) {
		return String.format("The entity of %s class doesn't contain value of the ID attribute, "
				+ "impossible to identify the entity in the table", entity.getClass().getName());
	}
	
	
}
