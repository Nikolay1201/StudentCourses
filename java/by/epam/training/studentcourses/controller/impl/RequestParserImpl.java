package by.epam.training.studentcourses.controller.impl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import by.epam.training.studentcourses.controller.RequestParser;
import by.epam.training.studentcourses.controller.constant.URLRequestParams;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.FiltrationType;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.User;

public class RequestParserImpl implements RequestParser {
	
	@Override
	public List<User> parseUsers(Map<String, String[]> paramsMap) {
//		UserBuilder userBuilder = new UserBuilder();
//		userBuilder.addID(paramsMap.get(Tables.Users.Attr.USER_ID.getAttrName())[0]);
//		userBuilder.addRegistrationDateTime(paramsMap.get(Tables.Users.Attr.REG_DATETIME.getAttrName())[0]);
//		userBuilder.addRole(paramsMap.get(Tables.Users.Attr.ROLE_ID.getAttrName())[0]);
//		userBuilder.addName(paramsMap.get(Tables.Users.Attr.NAME.getAttrName())[0]);
//		userBuilder.addSurename(paramsMap.get(Tables.Users.Attr.SURENAME.getAttrName())[0]);
//		userBuilder.addPatronymic(paramsMap.get(Tables.Users.Attr.NAME.getAttrName())[0]);
//		userBuilder.addBirthDate(paramsMap.get(Tables.Users.Attr.BIRTH_DATE.getAttrName())[0]);
//		userBuilder.addPhoneNumber(paramsMap.get(Tables.Users.Attr.PHONE_NUMBER.getAttrName())[0]);
//		userBuilder.addEmail(paramsMap.get(Tables.Users.Attr.EMAIL.getAttrName())[0]);
//		userBuilder.addLogin(paramsMap.get(Tables.Users.Attr.LOGIN.getAttrName())[0]);
//		userBuilder.addPassword(paramsMap.get(Tables.Users.Attr.PASSWORD.getAttrName())[0]);
//		userBuilder.addDesceiption(paramsMap.get(Tables.Users.Attr.DESCRIPTION.getAttrName())[0]);
//		return userBuilder.getUser();
		return null;
	}
	
	@Override 
	public Filter parseFiltrer(Map<String, String[]> paramsMap, boolean ignoreInvalidConditions) {
		Filter filter = new Filter();
		int i = 1;
		FiltrationType ftype;
		String attrName;
		String attrValue;
		while (paramsMap.containsKey(URLRequestParams.Filter.FILTRATION_TYPE + String.valueOf(i))) {
			try {
				ftype = FiltrationType.getByTextRepr(paramsMap.get(URLRequestParams.Filter.FILTRATION_TYPE + String.valueOf(i))[0]);
				attrName = paramsMap.get(URLRequestParams.Filter.ATTR_NAME + String.valueOf(i))[0];
				attrValue = paramsMap.get(URLRequestParams.Filter.ATTR_NAME + String.valueOf(i))[0];
				if (attrName == null || attrValue == null) {
					throw new NullPointerException();
				}
			} catch (NoSuchElementException | NullPointerException e) {
				if (ignoreInvalidConditions) {
					continue;
				} else {
					return null;
				}
			} 
			filter.addCondition(ftype, attrName, attrValue);
		}
		return filter;
	}

	@Override
	public List<Course> parseCourses(Map<String, String[]> paramsMap) {
		// stub
		return null;
	}

	@Override
	public List<CoursePlan> parseCoursePlans(Map<String, String[]> paramsMap) {
		// stub
		return null;
	}

	@Override
	public List<Lesson> parseLessons(Map<String, String[]> paramsMap) {
		// stub
		return null;
	}
	
}
