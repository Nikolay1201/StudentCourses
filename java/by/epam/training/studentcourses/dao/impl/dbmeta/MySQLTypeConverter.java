package by.epam.training.studentcourses.dao.impl.dbmeta;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.lang.model.type.UnknownTypeException;

public class MySQLTypeConverter {
	
	private MySQLTypeConverter() {}
	
	public static boolean isMySQLDateType(Class<?> cls) {
		return (cls == Double.class ||
			cls == String.class ||
			cls == Integer.class ||
			cls == LocalDateTime.class);
	}
	
	public static Class<?> mySQLDataTypeToInternalDataType(int dbDataType) throws UnknownTypeException {
		switch (dbDataType) {
		case (java.sql.Types.DOUBLE):
			return Double.class;
		case (java.sql.Types.LONGVARCHAR):
		case (java.sql.Types.VARCHAR):
			return String.class;
		case (java.sql.Types.INTEGER):
			return Integer.class;
		case (java.sql.Types.DATE):
			return LocalDateTime.class;
		default:	
			throw new UnknownTypeException(null, "Unsupported SQL data type \"" + 
					dbDataType + "\" was encountered");
		}
	}
	
	public static LocalDateTime toInternalDateTime(Timestamp dateTime) {
		return dateTime.toLocalDateTime();
	}
	
	public static Timestamp toMySQLDateTime(LocalDateTime dateTime) {
		return Timestamp.valueOf(dateTime);
	}
	
	public static Integer toInternalInt(int integerValue) {
		return Integer.valueOf(integerValue);
	}
	
	public static int toMySQLInt(Integer integerValue) {
		return integerValue.intValue();
	}
	
	public static Double toInternalDouble(double doubleValue) {
		return Double.valueOf(doubleValue);
	}
	
	public static double toMySQLDouble(Double doubleValue) {
		return doubleValue.doubleValue();
	}
	
	public static String toInternalString(String str) {
		return str;
	}
	
	public static String toMySQLText(String str) {
		return str;
	}	
	
	public static LocalDate toInternalDate(Date date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}
	
	public static Date toMySQLDate(LocalDate date) {
		return Date.valueOf(date);
	}

	public static Boolean toInternalBool(boolean bool) {
		return Boolean.valueOf(bool);
	}	
	
	public static boolean toMySQLBool(Boolean bool) {
		return bool.booleanValue();
	}

}
