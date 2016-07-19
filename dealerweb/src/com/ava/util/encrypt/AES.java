package com.ava.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private final static String key = "ASDFGHJKLzxcvbn6";

	private static String pad(String moduleName, String key) {
		int nameLength = moduleName.length();
		if (nameLength > 16) {
			return moduleName.substring(0, 16);
		}
		String result = moduleName + key.substring(nameLength);
		return result;
	}

	public static String decrypt(String sSrc, String moduleName, String key) {
		try {
			// 判断Key是否正确
			String sKey = pad(moduleName, key);
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = hex2byte(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 判断Key是否正确
	public static String encrypt(String sSrc, String moduleName, String key) {
		String sKey = pad(moduleName, key);
		if (sKey == null) {
			// System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			// System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw;
		try {
			raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) {
		String cSrc = "admin";
		String key = "ASDFGHJKLzxcvbn6";
		// 加密
		@SuppressWarnings("unused")
		long lStart = System.currentTimeMillis();
		String enString;
		try {
			enString = AES.encrypt(cSrc, "ems", key);
			System.out.println("加密后的字串是：" + enString);
			String DeString = AES.decrypt(enString, "ems", key);
			System.out.println("解密后的字串是：" + DeString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
