package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.TableAttr;

public class PrepStHelper {

	public static void fill(PreparedStatement ps, boolean skipNull, Object[] obj) throws SQLException {
		int j = 0;
		for (int i = 0; i < obj.length; i ++) {
			if (obj[i] == null) {
				if (skipNull) {
					j --;
				} else {	
					ps.setNull(j + 1, Types.VARCHAR);
				}
			} else if (obj[i].getClass() == String.class) {
				ps.setString(j + 1, MySQLTypeConverter.toMySQLText((String)obj[i]));
			} else if (obj[i].getClass() == Integer.class) {
				ps.setInt(j + 1, MySQLTypeConverter.toMySQLInt((Integer)obj[i]));
			} else if (obj[i].getClass() == Double.class) {
				ps.setDouble(j + 1, MySQLTypeConverter.toMySQLDouble((Double)obj[i]));
			} else if (obj[i].getClass() == LocalDateTime.class) {
				ps.setTimestamp(j + 1, MySQLTypeConverter.toMySQLDateTime((LocalDateTime)obj[i]));
			} else if (obj[i].getClass() == LocalDate.class) {
				ps.setDate(j + 1, MySQLTypeConverter.toMySQLDate((LocalDate)obj[i]));
			} else {
					throw new IllegalArgumentException("Unknown type \"" + obj[i].getClass().getName() + "\"");
			}
			j ++;
		}
	}
	
	public static void fill(PreparedStatement ps, boolean skipNull, Filter filter) throws SQLException {
		Object[] objects = new Object[filter.size()];
		for (int i = 0; i < filter.size(); i ++) {
			objects[i] = filter.getAttrValue(i);
		}
		fill(ps, skipNull, objects);
	}
	
	public static String genInsertStatement(String tableName, int parametersNum) {
		return String.format("INSERT INTO `%s` VALUES %s", tableName, expGen1(parametersNum));
	}
	
	public static String genSelectByFilterStatement(String tableName, Filter filter) {
		return "SELECT * FROM `" + tableName + "` WHERE " + filterToWhereClause(filter);
	}
	
	public static String genUpdateByIdStatement(String tableName, TableAttr[] attributes, 
			boolean[] nullAttributesStates, TableAttr idAttr) {
		if (attributes.length == 0 || attributes.length != nullAttributesStates.length) {
			throw new IllegalArgumentException();
		}
		int i = 0;
		for (i = 0; i < nullAttributesStates.length; i ++) {
			if (nullAttributesStates[i]) {
				break;
			}
		}
		if (i == nullAttributesStates.length) {
			return null;
		}
		String updSt = "UPDATE `" + tableName + "` SET ";
		for (i = 0; i < attributes.length; i ++) {
			if (nullAttributesStates[i] || attributes[i].getAttrName().equals(idAttr.getAttrName())) {
				continue;
			}
			updSt += ("`" + attributes[i].getAttrName() + "` = ?, ");
		}
		updSt = updSt.substring(0, updSt.length() - 2) + " WHERE `" + idAttr.getAttrName() + "` = ?";
		return updSt;
	}
	
	public static String genDeleteByIdStatement(String tableName, TableAttr idAttrName) {
		return String.format("DELETE FROM `%s` WHERE %s = ?", tableName, idAttrName.getAttrName());
	}
	
	private static String filterToWhereClause(Filter filter) {
		if (filter.size() == 0) {
			return "";
		}
		String whereClauseStr = "";
		String relationOperator;
		int i;
		for (i = 0; i < filter.size(); i ++) {
			switch(filter.getAttrFiltrationType(i)) {
			case LIKE:
				relationOperator = "LIKE";
				break;
			case GREATER_THAN:
				relationOperator = ">";
				break;
			case LESS_THAN:
				relationOperator = "<";
				break;
			case EQUALS:
				relationOperator = "=";
				break;
			default:
				throw new IllegalArgumentException();	
			}
			whereClauseStr += String.format("(`%s` %s ?)", filter.getAttrName(i), relationOperator); 
			if (i != filter.size() - 1) {
				whereClauseStr += " and ";
			}
		}
		return whereClauseStr;
	}
		
	// (?, ?, ..., ?) generator
	private static String expGen1(int n) {
		String expr = "(";
		for (int i = 0; i < n - 1; i ++) {
			expr = expr + "?, ";
		}
		return expr + "?)";
	}

}
