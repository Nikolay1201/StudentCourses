package by.epam.training.studentcourses.service.impl;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import by.epam.training.studentcourses.service.Hashing;

public class PBKDF2Hashing implements Hashing {
	
	private String salt;
	private Charset strCharset;
	private SecretKeyFactory skf;
	
	public PBKDF2Hashing(String salt, Charset strCharset) {
		this.salt = salt;
		this.strCharset = strCharset;
		 try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}		
	}

	@Override
	public String hashString(String str) {
		PBEKeySpec spec = new PBEKeySpec(str.toCharArray(), salt.getBytes(strCharset), 65536, 128);	
		try {
			return new String(skf.generateSecret(spec).getEncoded(), strCharset);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		
		}
	}

}
