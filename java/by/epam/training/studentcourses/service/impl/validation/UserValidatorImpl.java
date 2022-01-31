package by.epam.training.studentcourses.service.impl.validation;

import java.time.LocalDate;
import java.util.regex.Pattern;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;

public class UserValidatorImpl implements EntityValidator<User> {
	
	private static final int MIN_AGE = 14;
	private static final LocalDate MIN_BIRTH_DATE = LocalDate.parse("1920-01-01");
	private static final String PHONE_NUMBER_REG_EXP = "^(([0-9]){9,16})$";
	private static final String EMAIL_REG_EXP = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	private static final String LOGIN_REG_EXP = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
	private static final String PASSWORD_REG_EXP = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
	
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
		if (!validateEmali(user.getEmail())) {
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
		return (birthDate == null || 
			birthDate.compareTo(MIN_BIRTH_DATE) < 0 ||
			birthDate.compareTo(LocalDate.now().minusYears(MIN_AGE)) > 0);
	}
	
	private boolean validatePhoneNumber(String phoneNumber) {
		return (!Pattern.matches(PHONE_NUMBER_REG_EXP, phoneNumber));
	}
	
	private boolean validateEmali(String email) {
		return Pattern.matches(EMAIL_REG_EXP, email);
	}
	
	private boolean validateLogin(String login) {
		return Pattern.matches(LOGIN_REG_EXP, login);
	}
	
	private boolean validatePassword(String password) {
		return Pattern.matches(PASSWORD_REG_EXP, password);
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