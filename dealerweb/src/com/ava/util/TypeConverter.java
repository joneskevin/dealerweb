package com.ava.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TypeConverter {

	// private final static Logger log = Logger.getLogger(TypeConverter.class);

	public static Timestamp toTimestamp(String time) {
		if (time == null || "".equals(time)) {
			return null;
		} else {
			time = time.trim();
		}
		Timestamp s = null;
		DateFormat format = null;
		Date d = null;
		try {
			if (time.length() == 10) {
				format = new SimpleDateFormat("yyyy-MM-dd");
				d = format.parse(time);
				s = new Timestamp(d.getTime());
			}
			if (time.length() == 19) {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				d = format.parse(time);
				s = new Timestamp(d.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * @param value
	 * @return
	 */
	public static String toString(int value) {
		return toString(new Integer(value));
	}

	/**
	 * date to String
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String toString(java.util.Date date, String dateFormat) {

		if (date == null) {
			throw new IllegalArgumentException("[toString]date is null");
		}
		if (dateFormat == null) {
			throw new IllegalArgumentException("[toString]dateFormat is null");
		}

		SimpleDateFormat f = new SimpleDateFormat(dateFormat);

		return f.format(date);
	}

	/**
	 * @param intValue
	 * @return
	 */
	public static String toString(float f) {
		Float obj = null;
		try {
			obj = new Float(f);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}

	/**
	 * @param intValue
	 * @return
	 */
	public static String toString(Object o) {

		if (o == null) {
			return "";
		} else {
			return String.valueOf(o);
		}
	}

	/**
	 * String to Byte
	 * 
	 * @param intStr
	 * @return
	 */
	public static Byte toByte(String byteStr) {

		try {
			if (byteStr == null || byteStr.equalsIgnoreCase("")) {
				return null;
			} else {
				byteStr = byteStr.trim();
			}
			return new Byte(byteStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * String to Integer
	 * 
	 * @param str
	 * @return
	 */
	public static Integer toInteger(String intStr) {
		try {
			Double intDouble = toDouble(intStr);
			if (intDouble == null) {
				return null;
			} else {
				return intDouble.intValue();
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * int -> integer
	 * 
	 * @param i
	 * @return
	 */
	public static Integer toInteger(int i) {
		return Integer.valueOf(i);
	}

	public static Integer toInteger(Double intDouble) {
		if (intDouble == null) {
			return null;
		} else {
			return intDouble.intValue();
		}
	}

	public static Integer toInteger(Float intFloat) {
		if (intFloat == null) {
			return null;
		} else {
			return intFloat.intValue();
		}
	}

	public static Integer toInteger(Object o) {
		return toInteger(toString(o));
	}

	/**
	 * String to Long
	 * 
	 * @param str
	 * @return float 32 6-7 -3.4*10(-38)～3.4*10(38) double 64 15-16
	 *         -1.7*10(-308)～1.7*10(308) long double 128 18-19
	 *         -1.2*10(-4932)～1.2*10(4932)
	 */
	public static Long toLong(String longStr) {
		try {
			return new Long(longStr);
		} catch (Exception e) {
			return null;
		}
	}

	public static Long toLong(Integer longStr) {
		try {
			return new Long(longStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * convert str to Double object.
	 * 
	 * @param doubleStr
	 * @return
	 * @return float 32 6-7 -3.4*10(-38)～3.4*10(38) double 64 15-16
	 *         -1.7*10(-308)～1.7*10(308) long double 128 18-19
	 *         -1.2*10(-4932)～1.2*10(4932)
	 */
	public static Double toDouble(String doubleStr) {
		try {
			return Double.valueOf(doubleStr);
		} catch (Exception e) {
			return null;
		}
	}

	public static Double toDouble(Object o) {
		return toDouble(toString(o));
	}

	/**
	 * convert str to Double object.
	 * 
	 * @param doubleStr
	 * @return float 32 6-7 -3.4*10(-38)～3.4*10(38) double 64 15-16
	 *         -1.7*10(-308)～1.7*10(308) long double 128 18-19
	 *         -1.2*10(-4932)～1.2*10(4932)
	 */
	public static Float toFloat(String floatStr) {
		try {
			return new Float(floatStr);
		} catch (Exception e) {
			return null;
		}
	}

	public static Float toFloat(Integer integerValue) {
		try {
			return new Float(String.valueOf(integerValue));
		} catch (Exception e) {
			return null;
		}
	}

	public static Float toFloat(Object objectValue) {
		try {
			return new Float(String.valueOf(objectValue));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static int[] toInts(String[] intArray) {
		if(intArray == null){
			return null;
		}
		if(intArray.length == 0){
			return new int[0];
		}
		int[] ints = new int[intArray.length];
		for (int i = 0; i < intArray.length; i++) {
			ints[i] = toInteger(intArray[i]);
		}
		return ints;
	}

	public static List toList(Map inMap) {
		List resultList = new ArrayList();
		if (inMap == null) {
			return null;
		}
		Iterator itor = inMap.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry entry = (Map.Entry) itor.next();
			resultList.add(entry.getValue());
		}
		return resultList;
	}


    /**
     @author kingbull
     * @param Ids
     * @return
     */
    public static String generatePkSet(String[] ids){
    	StringBuffer pkSet = new StringBuffer();
    	if(ids == null || ids.length == 0){
    		return null;
    	}
    	
    	pkSet.append(ids[0]);
    	for(int i=1;i<ids.length;i++){
    		pkSet.append(",");
    		pkSet.append(ids[i]);
    	}
    	
    	return pkSet.toString();
    }
    /**
     @author kingbull
     * @param ids
     * @return
     */
    public static String generatePkSet(List ids){
    	StringBuffer pkSet = new StringBuffer();
    	if(ids == null || ids.size() == 0){
    		return null;
    	}
    	
    	Iterator itor = ids.iterator();
    	String id = toString((Integer)itor.next());
    	pkSet.append(id);
    	while(itor.hasNext()){
    		id = toString((Integer)itor.next());
    		pkSet.append(",");
    		pkSet.append(id);
    	}
    	
    	return pkSet.toString();
    } 
    
    /**
     * fun:"1,2,3,4" = "1,2" + "3,4"
     * @param pkSet1
     * @param pkSet2
     * @return
     */
    public static String mergePkSet(String pkSet1,String pkSet2){
    	String pkSet = null;
		if (pkSet2 == null || pkSet2.length() == 0) {
			if (pkSet1 != null && pkSet1.length() > 0) {
				pkSet = pkSet1;
			}			
		}   	
		if (pkSet1 == null || pkSet1.length() == 0) {
			if (pkSet2 != null && pkSet2.length() > 0) {
				pkSet = pkSet2;
			}			
		}  
		if ((pkSet1 != null && pkSet1.length() >= 0) && (pkSet2 != null && pkSet2.length() >= 0)) {
			pkSet = pkSet1 + "," + pkSet2;
		} 		
		return pkSet;
    }
    
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }
    /**
     * @param c
     * @param delimiter
     * @return
     */
    public static boolean contains(int[] objArray,int value){
        if (objArray == null || value < 0) {
            return false;
        }
		for (int i = 0; i < objArray.length; i++) {
			if(objArray[i] == value){
				return true;
			}
		}    	
		return false;
    }
    /**
     * @param c
     * @param delimiter
     * @return
     */
    public static String join(int[] intArray, String delimiter){
        if (intArray == null) {
            return null;
        }
    	List<String> strList = new ArrayList<String>();
		for (int i = 0; i < intArray.length; i++) {
			strList.add(String.valueOf(intArray[i]));
		}    	
		return join(strList,delimiter,null,null).toString();
    }
    /**
     * @param c
     * @param delimiter
     * @return
     */
    public static String join(String[] strArray, String delimiter){
        if (strArray == null) {
            return null;
        }
    	List<String> strList = new ArrayList<String>();
		for (int i = 0; i < strArray.length; i++) {
			strList.add(strArray[i]);
		}    	
		return join(strList,delimiter,null,null).toString();
    }
    /**
     * @author join element of collection with delimiter.
     * 			modified by 2006-3-7 18:04 ,add prefix & suffix parameter
     * @param collection
     * @param delimiter
     * @param prefix  -- if not null,it will be inserted at the begining of each element.
     * @param suffix  -- if not null,it will be appended at the end of each element
     * @return
     */
    
    public static String join(Collection c, String delimiter, String prefix, String suffix){

        StringBuffer buf = new StringBuffer();
        if (c != null && c.size()>0) {
            Iterator it = c.iterator();
            if (it.hasNext()) {
            	if (prefix!=null)
            		buf.append(prefix);
                buf.append(String.valueOf(it.next()));
                if (suffix!=null){
                	buf.append(suffix);
                }
            }
            while (it.hasNext()) {
            	Object value = it.next();
            	buf.append(delimiter);
            	if (prefix!=null){
            		buf.append(prefix);
            	}
            	if (value!=null){
            		buf.append(String.valueOf(value));	
            	}else{
            		buf.append(String.valueOf(""));
            	}
            	if (suffix!=null){
            		buf.append(suffix);
            	}                
            }
        }

        return buf.toString();
    }
    
    public static String join(Collection c, String delimiter){
    	return join(c, delimiter, null, null);
    }

	public static boolean isEqualIntValue(Object obj1, Object obj2) {
		Integer integer1 = toInteger(obj1);
		Integer integer2 = toInteger(obj2);
		if (integer1 == null || integer2 == null) {
			return false;
		}
		return integer1.intValue() == integer2.intValue();
	}

	/**
	 * if the size of a string is lager than zero
	 * 注意：判断前会截取字符串首尾的空字符
	 * @param input
	 * @return
	 */
	public static boolean sizeLagerZero(String input) {
		if (input != null) {
			input = input.trim();
			if (input.length() > 0) {
				return true;
			}
		}
		return false;

	}

	/**
	 * if the size of a string is lager than zero
	 * 
	 * @param input
	 * @return
	 */
	public static boolean sizeLagerZero(Integer input) {
		return sizeLagerZero(toString(input));

	}

	/**
	 * if the size of a string is lager than zero
	 * 
	 * @param input
	 * @return
	 */
	public static boolean sizeLagerZero(Float input) {
		return sizeLagerZero(toString(input));

	}

	/**
	 * @param messageType:处理类型
	 * @param returnCode:处理结果[0=failure][1=success][其他=customize]
	 * @param operationId:操作返回ID，一般用来传送新增记录的数据库ID
	 * @param levelId:层次等级ID，一般树形页面ajax的操作用
	 * @param extendContent:处理内容返回值，用来各个方法的扩展用
	 * @return
	 */
	public static String generateAjaxMessage(String messageType,
			String returnCode, String operationId, String levelId,
			String extendContent) {
		String message = null;
		if (returnCode == null) {
			message = messageType + "," + "0";
		} else {
			message = messageType + "," + returnCode;
		}
		if (operationId == null) {
			message = message + "," + "";
		} else {
			message = message + "," + operationId;
		}
		if (levelId == null) {
			message = message + "," + "";
		} else {
			message = message + "," + levelId;
		}
		if (extendContent == null) {
			message = message + "," + "";
		} else {
			message = message + "," + extendContent;
		}
		return message;
	}

	/**
	 * 按指定的位数保留double型数据“.”后面的尾数
	 * 
	 * @param dString
	 * @return
	 */
	public static String trimTailAfterDotByCount(Float dFloat, int count) {
		String dString = toString(dFloat);
		if (dString == null || dString.length() == 0) {
			return null;
		} else {
			dString = dString.trim();
		}

		int dotIndex = dString.lastIndexOf(".");
		if (dotIndex < 1) {
			return dString;
		}
		String tail = dString.substring(dotIndex + 1, dString.length());
		String body = dString.substring(0, dotIndex + 1);
		if (tail.length() > count) {
			tail = tail.substring(0, count);
		}
		return body.concat(tail);
	}

	/**
	 * 去掉double型数据的为“*.00”类型的尾巴
	 * 
	 * @param dString
	 * @return
	 */
	public static String trimZeroTail(Double value) {
		String dString = toString(value);
		return trimZeroTail(dString);
	}

	/**
	 * 去掉double型数据的为“*.00”类型的尾巴
	 * 
	 * @param dString
	 * @return
	 */
	public static String trimZeroTail(String dString) {
		if (dString == null || dString.length() == 0) {
			return null;
		} else {
			dString = dString.trim();
		}

		int tailFlag = 0;
		int dotIndex = dString.lastIndexOf(".");
		if (dotIndex < 0) {
			return dString;
		}
		char[] tail = dString.substring(dotIndex + 1, dString.length())
				.toCharArray();
		for (int i = 0; i < tail.length; i++) {
			if (tail[i] != '0') {
				tailFlag = 1;
				break;
			}
		}
		if (tailFlag != 1) {
			return dString.substring(0, dotIndex);
		}
		return dString;
	}

	public static String trimZeroTail(Timestamp dTimestamp) {
		if (dTimestamp == null) {
			return null;
		}
		return trimZeroTail(dTimestamp.toString());
	}

	public static String trimZeroTail(Date dTime) {
		if (dTime == null) {
			return null;
		}
		return trimZeroTail(dTime.toString());
	}

	/**	把double类型的数字转化为大写的人民币金额	*/
	public static String changeToBigAmount(double amount) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (amount * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
		if ("0".equals(valStr)){
			return "零圆";
		}
		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = 'x'; //值为'x'的时候表示之前没出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == 'x') { // 表示以前没出现过0
					zero = digit[0];
					if (idx == 0 && vidx > 0) {//个位的时候，并且不是末四位段
						prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
						zero = 'x';
					}
				} else {//表示以前出现过0
					if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
						prefix += vunit[vidx - 1];
						zero = 'x';
					}
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != 'x') { // 如果标志不为'x',则加上“零”
				prefix += zero;
				zero = 'x';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)//不是个位的时候
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {//个位的时候，并且不是末四位段
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}
		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}
	

    
    /**
     * 与操作:"2,3" = "1,2,3" + "2,3,4"
     * @param pks1
     * @param pks2
     * @return
     */
    public static Collection pksAndpks(Collection pks1,Collection pks2){
    	Collection resultCol = new HashSet();
    	if(pks1 != null && pks1.size() > 0 && pks2 != null && pks2.size() > 0){
    		Iterator itor = pks1.iterator();
    		while(itor.hasNext()){
    			Object ooo = itor.next();
    			if(pks2.contains(ooo)){
    				resultCol.add(ooo);
    			}
    		}
    		if(resultCol.size() == 0){
    			resultCol.add(new Integer(0));
    		}
    		return resultCol;
    	}
    	return null;
    }

	static public void main(String[] args) {
		int[] testArray = new int[3];
		testArray[0] = 12345;
		testArray[1] = 22345;
		testArray[2] = 18735;
		TypeConverter.contains(testArray,22345);
	}
}
