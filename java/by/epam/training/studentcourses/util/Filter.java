package by.epam.training.studentcourses.util;

import java.util.ArrayList;

/*
 * Filter allows to form "WHERE (attrName LIKE attrValue) and ..." clauses
 */
public class Filter implements Cloneable {
	
	private class FiltrationCondition {
		private String attrName;
		private String attrValue;
		private FiltrationType type;
		
		public FiltrationCondition(String attrName, String attrValue, FiltrationType type) {
			this.attrName = attrName;
			this.attrValue = attrValue;
			this.type = type;
		}

		public String getAttrName() {
			return attrName;
		}

		public String getAttrValue() {
			return attrValue;
		}

		public FiltrationType getType() {
			return type;
		}
	}
	
	private ArrayList<FiltrationCondition> filterList = new ArrayList<>();
	
	public void addCondition(FiltrationType filtrationType, String attrName, String attrValue) {
		if (attrName == null || filtrationType == null) {
			throw new IllegalArgumentException();
		}
		filterList.add(new FiltrationCondition(attrName, attrValue, filtrationType));
	}

	public void addCondition(TableAttr attr, String attrValue) {
		addCondition(FiltrationType.EQUALS, attr.getAttrName(), attrValue);
	}
	
	public void addCondition(TableAttr attr, Object attrValue) {
		if (attrValue instanceof String) {
			addCondition(attr, (String)attrValue);
		} else {
			addCondition(attr, attrValue.toString());
		}
	}
	
	public Filter() {}
	
	public Filter(String attrName, String attrValue) {
		addCondition(FiltrationType.EQUALS, attrName, attrValue);
	}
	
	public Filter(TableAttr attr, Object attrValue) {
		addCondition(attr, attrValue);
	}
	
	public Filter(FiltrationType filtrationType, String attrName, String attrValue) {
		addCondition(filtrationType, attrName, attrValue);
	}
	
	public Filter(TableAttr attr, String value) {
		this(attr.getAttrName(), value);
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
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(Filter.class.getName() + " [");
		if (filterList.isEmpty()) {
			return str.append("empty]").toString();
		}
		for (int i = 0; i < filterList.size(); i ++) {
			str.append(String.format("\n`%s` %s `%s`", filterList.get(i).getAttrName(), 
					filterList.get(i).getType().getStringRepr(), filterList.get(i).getAttrValue()));
		}
		return str.append("\n]").toString();
	}
	
	@Override
	public Filter clone() { 
		try {
			return (Filter)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
