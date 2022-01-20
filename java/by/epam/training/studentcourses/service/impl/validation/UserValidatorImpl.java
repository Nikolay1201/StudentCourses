package by.epam.training.studentcourses.service.impl.validation;

import java.time.LocalDate;
import java.util.regex.Pattern;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;

public class UserValidatorImpl implements EntityValidator<User> {
	
	private static final int minAge = 14;
	private static final LocalDate minBirthDate = LocalDate.parse("1920-01-01");
	private static final String phoneNumberRegExp = "^(([0-9]){9,16})$";
	private static final String emailRegExp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	private static final String loginRegExp = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
	private static final String passwordRegExp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
	
	@Override
	public Tables.Users.Attr validate(User user) {
		if (!validateName(user.getName())) {
			return Tables.Users.Attr.NAME;
		}
		if (!validateName(user.getSurename())) {
			return Tables.Users.Attr.SURENAME;
		}
		if (!validateName(user.getPatronymic())) {
			return Tables.Users.Attr.PATRONYMIC;
		}
		if (!validateBirthDate(user.getBirthDate())) {
			return Tables.Users.Attr.BIRTH_DATE;
		}
		if (!validatePhoneNumber(user.getPhoneNumber())) {
			return Tables.Users.Attr.PHONE_NUMBER;
		}
		if (!validateEmali(user.getEmali())) {
			return Tables.Users.Attr.EMAIL;
		}
		if (!validateLogin(user.getLogin())) {
			return Tables.Users.Attr.LOGIN;
		}
		if (!validatePassword(user.getPassword())) {
			return Tables.Users.Attr.PASSWORD;
		}
		return null;
	}
	
	private boolean validateBirthDate(LocalDate birthDate) {
		if (birthDate == null || 
				birthDate.compareTo(minBirthDate) < 0 ||
				birthDate.compareTo(LocalDate.now().minusYears(minAge)) > 0) {
			return false;
		}
		return true;
	}
	
	private boolean validatePhoneNumber(String phoneNumber) {
		if (!Pattern.matches(phoneNumberRegExp, phoneNumber)) {
			return false;
		}
		return true;
	}
	
	private boolean validateEmali(String email) {
		return Pattern.matches(emailRegExp, email);
	}
	
	private boolean validateLogin(String login) {
		return Pattern.matches(loginRegExp, login);
	}
	
	private boolean validatePassword(String password) {
		return Pattern.matches(passwordRegExp, password);
	}	
	
	private boolean validateName(String name) {
		if (name.length() <= 1 ||
			'A' < name.charAt(0) || name.charAt(0) > 'Z' ||
			!Pattern.matches("[a-z]", name.substring(1)) ||
			(name.length() == 2 && ('a' < name.charAt(0) || name.charAt(0) > 'z'))) {
			return false;
		}
		String name2 = name.toLowerCase();
		for (int i = 0; i <= name2.length() - 3; i ++) {
			if (name2.charAt(i) == name2.charAt(i + 1) && name2.charAt(i + 1) == name2.charAt(i + 2)) {
				return false;
			}
		}		
		return true;
	}

}