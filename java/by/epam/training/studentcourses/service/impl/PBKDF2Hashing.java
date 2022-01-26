package by.epam.training.studentcourses.service.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import by.epam.training.studentcourses.util.constant.MySQLDBParams;

public class PBKDF2Hashing implements Hashing {
	
	private static final int LENGTH = 32*4*4;
	private static final int CALCULATION_COMPLEXITY = 128;
	private String salt;
	private Charset strCharset;
	private SecretKeyFactory skf;

	
	public PBKDF2Hashing(String salt, Charset strCharset) {
		this.salt = salt;
		this.strCharset = strCharset;
		 try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchElementException();
		}		
	}

	@Override
	public String hashString(String str) {
		PBEKeySpec spec = new PBEKeySpec(str.toCharArray(), salt.getBytes(strCharset), 
				CALCULATION_COMPLEXITY, LENGTH);	
		try {
			return new String(skf.generateSecret(spec).getEncoded(), strCharset);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		Hashing h = new PBKDF2Hashing(MySQLDBParams.SALT, StandardCharsets.UTF_16);
		System.out.println(h.hashString("q"));
	}

}
