package by.epam.training.studentcourses.service.impl.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import by.epam.training.studentcourses.service.EntityValidator;
import by.epam.training.studentcourses.util.TableAttr;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;

public class UserValidatorImpl implements EntityValidator<User> {

	private static final int MIN_AGE = 14;
	private static final LocalDate MIN_BIRTH_DATE = LocalDate.parse("1920-01-01");
	private static final String PHONE_NUMBER_REG_EXP = "\\+(([0-9]){9,16})$";
	private static final String EMAIL_REG_EXP = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
	private static final String LOGIN_REG_EXP = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$";
	private static final String PASSWORD_REG_EXP = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

	@Override
	public List<TableAttr> validate(User user, boolean skipNull) {
		List<TableAttr> invalidAttrList = new ArrayList<>();
		if ((user.getName() == null && !skipNull)||
				(user.getName() != null && !validateName(user.getName()))) {
			invalidAttrList.add(Tables.Users.Attr.NAME);
		}
		if ((user.getSurename() == null && !skipNull)||
				(user.getSurename() != null && !validateName(user.getSurename()))) {
			invalidAttrList.add(Tables.Users.Attr.SURENAME);
		}
		if ((user.getPatronymic() == null && !skipNull)||
				(user.getPatronymic() != null && !validateName(user.getPatronymic()))) {
			invalidAttrList.add(Tables.Users.Attr.PATRONYMIC);
		}
		if ((user.getBirthDate() == null && !skipNull)||
				(user.getBirthDate() != null && !validateBirthDate(user.getBirthDate()))) {
			invalidAttrList.add(Tables.Users.Attr.BIRTH_DATE);
		}
		if ((user.getPhoneNumber() == null && !skipNull)||
				(user.getPhoneNumber() != null && !validatePhoneNumber(user.getPhoneNumber()))) {
			invalidAttrList.add(Tables.Users.Attr.PHONE_NUMBER);
		}
		if ((user.getEmail() == null && !skipNull)||
				(user.getEmail() != null && !validateEmali(user.getEmail()))) {
			invalidAttrList.add(Tables.Users.Attr.EMAIL);
		}
		if ((user.getLogin() == null && !skipNull)||
				(user.getLogin() != null && !validateLogin(user.getLogin()))) {
			invalidAttrList.add(Tables.Users.Attr.LOGIN);
		}
		if ((user.getPassword() == null && !skipNull)||
				(user.getPassword() != null && !validatePassword(user.getPassword()))) {
			invalidAttrList.add(Tables.Users.Attr.PASSWORD);
		}
		return invalidAttrList;
	}

	private boolean validateBirthDate(LocalDate birthDate) {
		return (birthDate == null || birthDate.compareTo(MIN_BIRTH_DATE) < 0
				|| birthDate.compareTo(LocalDate.now().minusYears(MIN_AGE)) < 0);
	}

	private boolean validatePhoneNumber(String phoneNumber) {
		return (Pattern.matches(PHONE_NUMBER_REG_EXP, phoneNumber));
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
		if (name.length() <= 1 || 'А' > name.charAt(0) || name.charAt(0) > 'Я'
				|| !Pattern.matches("[а-я]+", name.substring(1))
				|| (name.length() == 2 && ('а' < name.charAt(0) || name.charAt(0) > 'я'))) {
			return false;
		}
		String name2 = name.toLowerCase();
		for (int i = 0; i <= name2.length() - 3; i++) {
			if (name2.charAt(i) == name2.charAt(i + 1) && name2.charAt(i + 1) == name2.charAt(i + 2)) {
				return false;
			}
		}
		return true;
	}

}