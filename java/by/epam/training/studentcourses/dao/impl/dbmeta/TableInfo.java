package by.epam.training.studentcourses.dao.impl.dbmeta;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TableInfo {
	private String name;
	private List<AttrInfo> attrInfoList = new ArrayList<>();
	
	public TableInfo(String name) {
		this.name = name;
	}
	
	public void addAttrInfo(AttrInfo attrInfo) {
		if (attrInfo == null) {
			throw new IllegalArgumentException();
		}
		attrInfoList.add(attrInfo);
	}
	
	public void addAttrInfo(List<AttrInfo> attrInfoList) {
		if (attrInfoList == null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < attrInfoList.size(); i ++) {
			addAttrInfo(attrInfoList.get(i));
		}
	}
	
	public List<AttrInfo> getAttrInfoList() {
		return attrInfoList;
	}
	
	public AttrInfo getAttrInfoByName(String attrName) {
		for (int i = 0; i < attrInfoList.size(); i ++) {
			if (attrInfoList.get(i).getName().equals(attrName)) {
				return attrInfoList.get(i);
			}	
		}
		throw new NoSuchElementException("There is no attr with name `" + 
				attrName + "` in table " + getName());
	}

	public String getName() {
		return name;
	}	
	
	public int getAttrNumber() {
		return attrInfoList.size();
	}
	
	public Class<?> getAttrType(int index) {
		return attrInfoList.get(index).getType();
	}
	
}
