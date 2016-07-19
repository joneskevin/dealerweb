package com.ava.util;

import java.util.UUID;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;

public class MyRandomUtils extends RandomUtils{
	
    private static char[] NUMBERS_AND_LETTERS = ("23456789abcdefghjkmnpqrstuvwxyz" +
            "23456789ABCDEFGHJKMNPQRSTUVWXYZ").toCharArray();

	/**	取19位长度的长整型，头13位是精确到毫秒的系统当前时间，后6位是随机数，随机数不足6位则右侧补零	*/
	public static Long getTimeRandomLong() {
		return NumberUtils.toLong(getTimeRandomString());
	}
	/**	取19位长度的数字字符串，头13位是精确到毫秒的系统当前时间，后6位是随机数，随机数不足6位则右侧补零	*/
	public static String getTimeRandomString() {
		String current = Long.toString(System.currentTimeMillis());
		current = current + Integer.toString(nextInt(999999));
		current = MyStringUtils.rightPad(current, 19, "0");
		return current;
	}
	
	/**	生成32位长度的UUID字符串，去掉了默认的横杠("-")符号  */
	public static String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.replaceAll("-", "");
    }
    public static final String randomString() {
    	return randomString(8);
    }
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = NUMBERS_AND_LETTERS[nextInt(NUMBERS_AND_LETTERS.length)];
        }
        return new String(randBuffer);
    }
    
    /**	生成min到max闭合区间的随机整数	*/
    public static int nextInt(int min, int max) {
    	return nextInt(max-min+1) + min;
    }

	public static void main(String[] args) {
		
		for (int i=1; i <= 100000; i++){
			System.out.println(nextInt(10, 20));
		}
		System.out.println("finish");

	}
	
}
