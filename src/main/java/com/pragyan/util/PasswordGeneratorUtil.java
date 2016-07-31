package com.pragyan.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class PasswordGeneratorUtil {

	public String generatePassword(String password) throws NoSuchAlgorithmException {

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");  
		messageDigest.update(password.getBytes(),0, password.length());  
		String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);  
		if (hashedPass.length() < 32) {
		   hashedPass = "0" + hashedPass; 
		}
		return hashedPass;
	}
}
