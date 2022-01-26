package by.epam.training.studentcourses.controller.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.RequestParams;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.FiltrationType;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class EntityParserImpl implements EntityParser {
	
	private static EntityParser instance = new EntityParserImpl();
	
	public static EntityParser getInstance() {
		return instance;
	}
	
	private EntityParserImpl() {}
	
	@Override
	public List<User> parseUsers(Map<String, String[]> paramsMap) throws InvalidRequestException {
		List<User> usersList = new ArrayList<>();
		String[] idsArr = paramsMap.get(Tables.Users.Attr.USER_ID.getAttrName());
		if (idsArr == null) {
			return usersList;
		}
		User user;
		try {
			for (int i = 0; i < idsArr.length; i ++)  {
				user = new User();
				user.setId(Integer.parseInt(paramsMap.get(Tables.Users.Attr.USER_ID.getAttrName())[i]));
				user.setRegistrationDateTime(
						LocalDateTime.parse(paramsMap.get(Tables.Users.Attr.REG_DATETIME.getAttrName())[i]));
				user.setRole(
						UserRole.valueOf(paramsMap.get(Tables.Users.Attr.ROLE_ID.getAttrName())[i]));
				user.setName(paramsMap.get(Tables.Users.Attr.NAME.getAttrName())[i]);
				user.setSurename(paramsMap.get(Tables.Users.Attr.SURENAME.getAttrName())[i]);
				user.setPatronymic(paramsMap.get(Tables.Users.Attr.NAME.getAttrName())[i]);
				user.setBirthDate(
						LocalDate.parse(paramsMap.get(Tables.Users.Attr.BIRTH_DATE.getAttrName())[i]));
				user.setPhoneNumber(paramsMap.get(Tables.Users.Attr.PHONE_NUMBER.getAttrName())[i]);
				user.setEmail(paramsMap.get(Tables.Users.Attr.EMAIL.getAttrName())[i]);
				user.setLogin(paramsMap.get(Tables.Users.Attr.LOGIN.getAttrName())[i]);
				user.setPassword(paramsMap.get(Tables.Users.Attr.PASSWORD.getAttrName())[i]);
				user.setDesciption((paramsMap.get(Tables.Users.Attr.DESCRIPTION.getAttrName())[i]));
				usersList.add(user);
			}
		} catch (RuntimeException e) {
			throw new InvalidRequestException(e);
		}
		return usersList;
	}
	
	@Override
	public Filter parseFilter(Map<String, String[]> paramsMap) throws InvalidRequestException {
		Filter filter = new Filter();
		FiltrationType filtrationType;
		String attrName;
		String attrValue;
		String[] conditionsNamesArr = paramsMap.get(RequestParams.Filter.ATTR_NAME);
		if (conditionsNamesArr == null) {
			return filter;
		}
		for (int i = 0; i < conditionsNamesArr.length; i ++) {
			try {
				filtrationType = FiltrationType.getByTextRepr(
						paramsMap.get(RequestParams.Filter.FILTRATION_TYPE)[i]);
				attrName = paramsMap.get(RequestParams.Filter.ATTR_NAME)[i];
				attrValue = paramsMap.get(RequestParams.Filter.ATTR_NAME)[i];
				if (attrName == null || attrValue == null) {
					throw new NullPointerException();
				}
				filter.addCondition(filtrationType, attrName, attrValue);
			} catch (RuntimeException e) {
				throw new InvalidRequestException(e);
			} 
		}
		return filter;
	}

	@Override
	public List<Course> parseCourses(Map<String, String[]> paramsMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoursePlan> parseCoursePlans(Map<String, String[]> paramsMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lesson> parseLessons(Map<String, String[]> paramsMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
