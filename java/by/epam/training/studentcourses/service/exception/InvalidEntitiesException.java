package by.epam.training.studentcourses.service.exception;

import java.util.ArrayList;
import java.util.List;

import by.epam.training.studentcourses.util.TableAttr;

public class InvalidEntitiesException extends ServiceException {
	private final List<List<TableAttr>> invalidAttrLists;

	public InvalidEntitiesException(List<List<TableAttr>> invalidAttrLists) {
		this.invalidAttrLists = invalidAttrLists;
	}

	public InvalidEntitiesException(Exception e) {
		super(e);
		invalidAttrLists = new ArrayList<>();
	}

	public List<List<TableAttr>> getInvalidAttrsLists() {
		return invalidAttrLists;
	}

}
