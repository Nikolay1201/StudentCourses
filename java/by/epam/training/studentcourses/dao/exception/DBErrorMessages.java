package by.epam.training.studentcourses.dao.exception;

import by.epam.training.studentcourses.util.Filter;

public class DBErrorMessages {
		
	public static String getInvalidTableAttributeMessage(
		String tableName, String attrName) { 
		return String.format("There is no attribute `%s` in the table `%s`",
				attrName, tableName);
	}
	
	public static String getTableNotExistsMessage(String tableName) { 
		return String.format("Error in code: Table `%s` does not exists", tableName);
	}
	
	public static String getFilterDoesntMatchTableMessage(String tableName, Filter filter) {
		return String.format("The following filter doesn't matches the table `%s`%n%s", 
				tableName, filter.toString());
	}
	
	public static String genIdIsNotDefinedMessage(String entitysClassName) {
		return String.format("Operation with entity %s is forbidden: ID is not defined. "
				+ "Impossible to identify the entity.", entitysClassName);
	}
	
	public static String getUnsupportedTypeEncounderedMessage(String typeName) {
		return "Unknown type \"" + typeName + "\"";
	}
	
	private DBErrorMessages() {}
	
	
}
