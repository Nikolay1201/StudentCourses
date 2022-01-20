package by.epam.training.studentcourses.dao.impl.dbmeta;

public class AttrInfo {
	private String name;
	private Class<?> type;
	// is primary key
	private boolean isPK = false;
	// the only way to be not editable is to be auto incremented
	private boolean isEditable = true;

	public AttrInfo(String name, Class<?> type) {
		if (name == null || type == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public boolean isPK() {
		return isPK;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}	
	
	
	
}
