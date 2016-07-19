package com.ava.util;

import java.util.Random;

public class ValidationCodeUtil {

	public static String nextValidationCode() {
		return nextValidationCode(4);
	}

	public static String nextValidationCode(int n) {
		String vCode = "";
		// int n=4;
		for (int i = 0; i < n; i++) {
			Random random = new Random();
			if ((int) (Math.random() * 10) > 4) {
				int nInt = random.nextInt();
				int mInt = Math.abs(nInt);
				String tmpCode = "" + mInt;
				vCode += tmpCode.substring(0, 1);
			} else {
				int temp = 65 + random.nextInt(26);
				String value = String.valueOf((char) temp);
				if (value.equals("O") || value.equals("I") || value.equals("l"))
					i--;
				else
					vCode += value;
			}

		}
		return vCode;
	}

	/**
	 * @author kingbull
	 * @return
	 */
	public static String nextPassword() {
		String vCode = null;
		Random random = new Random();
		int nInt = random.nextInt();
		int mInt = Math.abs(nInt);
		vCode = "" + mInt;
		vCode = vCode.substring(0, 6);
		return vCode;
	}
}
