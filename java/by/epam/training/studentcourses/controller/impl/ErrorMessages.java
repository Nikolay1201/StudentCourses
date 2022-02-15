package by.epam.training.studentcourses.controller.impl;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.util.entity.UserRole;

public class ErrorMessages {
	
	private static final String BASE_NAME = "error";
	private static final Locale DEFAULT_LOCALE = new Locale(ContextParams.Session.Locale.LANG_EN);
	private ResourceBundle messages;
	
	public ErrorMessages(HttpServletRequest request) {
		Locale locale = (Locale)request.getSession().getAttribute(ContextParams.Session.LOCALE);
		if (locale == null) {
			locale = DEFAULT_LOCALE;
		}
		messages = ResourceBundle.getBundle(BASE_NAME, locale);
	}

	public String invalidLogin() {
		return messages.getString("invalidLogin");
	}

	public String invalidPassword() {
		return messages.getString("invalidPassword");
	}
	
	public String notAuthorized() {
		return messages.getString("notAuthorized");
	}

	public String internalError() {
		return messages.getString("internalError");
	}

	public String pageNotFound() {
		return messages.getString("pageNotFound");
	}

	public String invalidRequest(String invalidParamsInfo) {
		String str = messages.getString("invalidRequest");
		if (invalidParamsInfo != null) {
			str += "\n" + invalidParamsInfo;
		}
		return str;
	}

	public String loginAlreadExists(String login) {
		return String.format(messages.getString("loginAlreadExists"), login);
	}
	
	public String unknownError() {
		return messages.getString("unknownError");
	}

	public String notAllowed(List<UserRole> allowedRoleList) {
		StringBuilder str = new StringBuilder(messages.getString("notAllowed"));
		for (UserRole role : allowedRoleList) {
			str.append("\n");
			str.append(role.toString());
		}
		return str.toString();
	}

}
