package by.epam.training.studentcourses.service;

import java.nio.charset.StandardCharsets;

import by.epam.training.studentcourses.service.impl.PBKDF2Hashing;
import by.epam.training.studentcourses.util.constant.MySQLDBParams;

public class HashingFactory {
	
	private static Hashing instance = new PBKDF2Hashing(MySQLDBParams.SALT, StandardCharsets.UTF_16);
	
	public static Hashing getInstance() {
		return instance;
	}
	
	private HashingFactory() {}
	
}
