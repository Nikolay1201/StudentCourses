package by.epam.training.studentcourses.util;

import java.util.ArrayList;

/*
 * Filter allows to form "WHERE (attrName LIKE attrValue) and ..." clauses
 */
public class Filter {
	
	private class FiltrationCondition {
		private String attrName;
		private String attrPattern;
		private FiltrationType type;
		
		public FiltrationCondition(String attrName, String attrValue, FiltrationType type) {
			this.attrName = attrName;
			this.attrPattern = attrValue;
			this.type = type;
		}

		public String getAttrName() {
			return attrName;
		}

		public String getAttrValue() {
			return attrPattern;
		}

		public FiltrationType getType() {
			return type;
		}
	}
	
	private ArrayList<FiltrationCondition> filterList = new ArrayList<FiltrationCondition>();
	
	public void addCondition(FiltrationType type, String attrName, String attrValue) {
		if (attrName == null || type == null) {
			throw new IllegalArgumentException();
		}
		filterList.add(new FiltrationCondition(attrName, attrValue, type));
	}
	
	public int size() {
		return filterList.size();
	}
	
	public String getAttrName(int index) {
		return filterList.get(index).getAttrName();
	}
	
	public String getAttrValue(int index) {
		return filterList.get(index).getAttrValue();
	}
	
	public FiltrationType getAttrFiltrationType(int index) {
		return filterList.get(index).getType();
	}
	
	public String getStringRepr() {
		if (filterList.isEmpty()) {
			return "[empty]";
		}
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < filterList.size(); i ++) {
			str.append(String.format("%s %s %s%n", filterList.get(i).getAttrName(), 
					filterList.get(i).getType().getStringRepr(), filterList.get(i).getAttrValue()));
		}
		return str.toString();
	}
	
	
}
