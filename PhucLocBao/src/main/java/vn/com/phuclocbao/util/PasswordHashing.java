package vn.com.phuclocbao.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;

public class PasswordHashing {
	public static String hashMD5(String passwordToHash) throws BusinessException {
		String generatedPassword = null;
		// Create MessageDigest instance for MD5
		try {
			MessageDigest md;
				md = MessageDigest.getInstance("MD5");
			
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
	
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException(PLBErrorCode.CAN_NOT_HASHING_PASSWORD.name());
		}
		return generatedPassword;
	}
}
