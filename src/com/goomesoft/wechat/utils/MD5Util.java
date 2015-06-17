package com.goomesoft.wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static MessageDigest messageDigest;
	private static StringBuilder sb;
	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		sb = new StringBuilder();
	}

	private MD5Util() {
	}

	/**
	 * Return a hash according to the MD5 algorithm of the given String.
	 * @param s The String whose hash is required
	 * @return The MD5 hash of the given String
	 */
	public static String md5(String s) {
		messageDigest.reset();
		messageDigest.update(s.getBytes());
		byte digest[] = messageDigest.digest();
		sb.setLength(0);
		for (int i = 0; i < digest.length; i++) {
			final int b = digest[i] & 255;
			if (b < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(b));
		}
		return sb.toString();
	}
}