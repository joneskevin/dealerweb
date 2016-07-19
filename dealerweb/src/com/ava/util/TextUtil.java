package com.ava.util;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

	/** 处理字符串内容，把一些特殊符号转换成html标签 */
	public static String enableHtmlTagFilter(String input) {
		if (input == null) {
			return "";
		}
		char s[] = input.toCharArray();
		int length = s.length;
		StringBuffer ret = new StringBuffer(length);
		for (int i = 0; i < length; i++)
			if (s[i] == '&') {
				if (i + 3 < length && s[i + 1] == 'l' && s[i + 2] == 't'
						&& s[i + 3] == ';') {
					ret.append('<');
					i += 3;
				} else if (i + 3 < length && s[i + 1] == 'g' && s[i + 2] == 't'
						&& s[i + 3] == ';') {
					ret.append('>');
					i += 3;
				} else if (i + 4 < length && s[i + 1] == 'a' && s[i + 2] == 'm'
						&& s[i + 3] == 'p' && s[i + 4] == ';') {
					ret.append('&');
					i += 4;
				} else if (i + 5 < length && s[i + 1] == 'q' && s[i + 2] == 'u'
						&& s[i + 3] == 'o' && s[i + 4] == 't'
						&& s[i + 5] == ';') {
					ret.append('"');
					i += 5;
				} else {
					ret.append('&');
				}
			} else {
				ret.append(s[i]);
			}

		return ret.toString();
	}

	/** 处理字符串内容，把html标签转换成普通文本输出 */
	public static String disableHtmlTagFilter(String input) {
		if (input == null) {
			return "";
		}
		char s[] = input.toCharArray();
		int length = s.length;
		StringBuffer ret = new StringBuffer(length + 100);
		for (int i = 0; i < length; i++)
			if (s[i] == '<')
				ret.append("&lt;");
			else if (s[i] == '>')
				ret.append("&gt;");
			else if (s[i] == '&') {
				if (i + 3 < length && s[i + 1] == '#' && s[i + 2] >= '0'
						&& s[i + 1] <= '9' && s[i + 3] >= '0'
						&& s[i + 2] <= '9')
					ret.append(s[i]);
				else if (i + 3 < length && s[i + 1] == 'l' && s[i + 2] == 't'
						&& s[i + 3] == ';')
					ret.append(s[i]);
				else if (i + 3 < length && s[i + 1] == 'g' && s[i + 2] == 't'
						&& s[i + 3] == ';')
					ret.append(s[i]);
				else if (i + 4 < length && s[i + 1] == 'a' && s[i + 2] == 'm'
						&& s[i + 3] == 'p' && s[i + 4] == ';')
					ret.append(s[i]);
				else if (i + 5 < length && s[i + 1] == 'q' && s[i + 2] == 'u'
						&& s[i + 3] == 'o' && s[i + 4] == 't'
						&& s[i + 5] == ';')
					ret.append(s[i]);
				else
					ret.append("&amp;");
			} else if (s[i] == '"')
				ret.append("&quot;");
			else
				ret.append(s[i]);

		return ret.toString();
	}

	/** 只获取html代码中的文字内容，过滤html标签效果 */
	public static String htmlToStr(String htmlStr) {
		if (htmlStr == null) {
			return null;
		}

		String regEx = "<.+?>"; // 表示标签
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(htmlStr);
		String s = m.replaceAll("");

		return s;
	}

	/**
	 * 处理html字符串，如果超出长度，则截取相应长度并在最后加"..." 同时会保留html代码用于前台显示 裁截 <font
	 * color=red>title </font>的长度
	 */
	public static String formatHtmlStr(String input, int len) {
		if (input == null) {
			return "";
		}
		String regexStr = "(<[\\p{Alnum}|\\p{Punct}|\\s]+?>)(.+?)(<[\\p{Alnum}|\\p{Punct}|\\s]+?>)";
		Pattern tagPattern = Pattern.compile(regexStr);
		StringBuffer result = new StringBuffer();
		Matcher matcher = tagPattern.matcher(input);
		while (matcher.find()) {
			result.append(matcher.group(1));
			result.append(substringByWidth(matcher.group(2), len));// 按长度截断
			result.append(matcher.group(3));
		}
		if (result.length() == 0) {
			return substringByWidth(input, len);
		}
		return result.toString();
	}

	/**
	 * 处理字符串，如果超出长度则截取，长度为英文字符长度，如果是中文，则占2个字符长度
	 */
	public static final String substring(String str, int len) {
		if (str == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 255) {
				counter++;
			} else {
				counter = counter + 2;
			}
			if (counter > len) {
				break;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 处理字符串，如果超出长度，则截取相应长度并在最后加"..." 长度为中文字符长度，如果是数字或英文，则占半个字符长度
	 * 此方法主要用户页面的新闻标题等截断显示，同时保证了截断后字符串的显示长度一致性
	 */
	public static final String substringByWidth(String str, int len) {
		if (str == null) {
			return "";
		}
		boolean isStrEnd = true;
		len = len * 2;
		StringBuffer sb = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 255) {
				counter++;
			} else {
				counter = counter + 2;
			}
			if (counter > len) {
				isStrEnd = false;
				break;
			}
			sb.append(c);
		}
		if (isStrEnd) {
			return sb.toString();
		} else {
			return sb.toString() + "...";
		}
	}

	/**
	 * 替换原先字符串中相应的内容，这个方法替代默认的String.replaceAll()是因为原先方法容易在替换内容有特殊字符时失效 @
	 * 示例：content.replaceAll( "显示图片：<IMG
	 * src="http://kingsh.local.com:8080/upload/2007/03/23/1174639526765.jpg">",
	 * "http://kingsh.local.com:8080", "http://www.kingsh.com" ); @
	 * return："显示图片：<IMG
	 * src="http://www.kingsh.com/upload/2007/03/23/1174639526765.jpg">"
	 */
	public static String replaceAll(String content, String regex,
			String replacement) {
		if (content == null)
			return null;
		StringBuffer stringbuffer = new StringBuffer();
		int i = content.length();
		int j = regex.length();
		int k;
		int l;
		for (k = 0; (l = content.indexOf(regex, k)) >= 0; k = l + j) {
			stringbuffer.append(content.substring(k, l));
			stringbuffer.append(replacement);
		}

		if (k < i)
			stringbuffer.append(content.substring(k));
		return stringbuffer.toString();
	}

	/**
	 * 从带有分隔符的数组中得到一个指定ID的名值对 示例：getPairFromArrayWithDelimiter({1-电视机,2-冰箱}, -,
	 * 2) return：{2,冰箱} ,返回的数组只有2个元素
	 */
	static public String[] getPairFromArrayWithDelimiter(String[] strArray,
			String delimiter, Integer itemId) {
		if (strArray == null || strArray.length == 0) {
			return null;
		}
		String id = null;
		if (delimiter == null || itemId == null) {
			return null;
		} else {
			id = itemId.toString();
		}
		for (int i = 0; i < strArray.length; i++) {
			String item = strArray[i];
			if (item != null && item.length() > 0) {
				String[] pair = item.split(delimiter);
				if (pair != null && pair.length > 1) {
					String pairId = pair[0];
					if (pairId.equalsIgnoreCase(id)) {
						return pair;
					}
				}
			}
		}
		return null;
	}

	/** 把textarea输入的换行转化成html的换行标签 */
	public static final String convertBreakLineTag(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 10) {
				continue;
			}
			if (c == 13) {
				sb.append("<br>");
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/** 把textarea输入的换行去除 */
	public static final String disableBreakLineTag(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 10) {
				continue;
			}
			if (c == 13) {
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/** 把一些不可视的字符转成html标签，比如空格、回车等 */
	public static final String convertInvisibleTag(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 10) {
				// sb.append("<br>");
				continue;
			}
			if (c == 13) {
				sb.append("<br>");
				continue;
			}
			if (c == 32) {
				sb.append("&nbsp;");
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/** 把空格消除，用来处理作为参数传递的字符串 */
	public static final String disableBlankTag(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 32) {
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/** 把文本按换行分割成数组 */
	public static final List toArrayByBreakLine(String str) {
		if (str == null) {
			return null;
		}
		List itemList = new ArrayList();
		int startIndex = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 13) {
				itemList.add(str.substring(startIndex, i));
				startIndex = i + 1;
			}
		}
		itemList.add(str.substring(startIndex));
		return itemList;
	}

	/**
	 * 把帖子内容转化成帖子要求的格式文本的输出 1、过滤了用户手动收入的html代码，将其用普通文本输出。
	 * 2、把允许的几种bbscode转化为html代码，如加粗、斜体等。 3、把文本框输入的换行转为html代码
	 */
	public static final String formatBbsPost(String str) {
		String myStr = disableHtmlTagFilter(str);
		myStr = BbsCodeFilter.enableBbsCodeFilter(myStr);
		myStr = BbsCodeFilter.enableUrlFilter(myStr);

		myStr = convertBreakLineTag(myStr);
		return myStr;
	}

	/** 获取由Pad组成的指定长度的字符数组 */
	private static char[] createPadArray(char pad, int size) {
		if (size < 1) {
			return null;
		}
		StringBuilder buf = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			buf.append(pad);
		}
		return buf.toString().toCharArray();
	}

	/** 把不足length长度的字符串，按length指定的长度，在右边补pad */
	public static String padOnLeft(String string, char pad, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuilder buf = new StringBuilder(length);
		buf.append(createPadArray(pad, length), 0, length - string.length()).append(string);
		return buf.toString();
	}

	/** 把不足length长度的字符串，在左边补零 */
	public static String padZeroOnLeft(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		String buf = padOnLeft(string, ' ', length);
		return buf.toString();
	}

	/**
	 * Turns an array of bytes into a String representing each byte as an
	 * unsigned hex number.
	 * <p/>
	 * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
	 * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
	 * Distributed under LGPL.
	 * 
	 * @param bytes
	 *            an array of bytes to convert to a hex-string
	 * @return generated hex string
	 */
	public static String encodeHex(byte[] bytes) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * Turns a hex encoded string into a byte array. It is specifically meant to
	 * "reverse" the toHex(byte[]) method.
	 * 
	 * @param hex
	 *            a hex encoded String to transform into a byte array.
	 * @return a byte array representing the hex String[
	 */
	public static byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			int newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = (byte) newByte;
			byteCount++;
		}
		return bytes;
	}

	/**
	 * Returns the the byte value of a hexadecmical char (0-f). It's assumed
	 * that the hexidecimal chars are lower case as appropriate.
	 * 
	 * @param ch
	 *            a hexedicmal character (0-f)
	 * @return the byte value of the character (0x00-0x0F)
	 */
	private static byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	/**
	 * 把类似“squall@kingsh”转义成“squall\40kingsh”
	 * <p>
	 * <p>
	 * Escapes the node portion of a JID according to "JID Escaping" (JEP-0106).
	 * Escaping replaces characters prohibited by node-prep with escape
	 * sequences, as follows:
	 * <p>
	 * 
	 * <table border="1">
	 * <tr>
	 * <td><b>Unescaped Character</b></td>
	 * <td><b>Encoded Sequence</b></td>
	 * </tr>
	 * <tr>
	 * <td>&lt;space&gt;</td>
	 * <td>\20</td>
	 * </tr>
	 * <tr>
	 * <td>"</td>
	 * <td>\22</td>
	 * </tr>
	 * <tr>
	 * <td>&</td>
	 * <td>\26</td>
	 * </tr>
	 * <tr>
	 * <td>'</td>
	 * <td>\27</td>
	 * </tr>
	 * <tr>
	 * <td>/</td>
	 * <td>\2f</td>
	 * </tr>
	 * <tr>
	 * <td>:</td>
	 * <td>\3a</td>
	 * </tr>
	 * <tr>
	 * <td>&lt;</td>
	 * <td>\3c</td>
	 * </tr>
	 * <tr>
	 * <td>&gt;</td>
	 * <td>\3e</td>
	 * </tr>
	 * <tr>
	 * <td>@</td>
	 * <td>\40</td>
	 * </tr>
	 * <tr>
	 * <td>\</td>
	 * <td>\5c</td>
	 * </tr>
	 * </table>
	 * <p>
	 * 
	 * This process is useful when the node comes from an external source that
	 * doesn't conform to nodeprep. For example, a username in LDAP may be "Joe
	 * Smith". Because the &lt;space&gt; character isn't a valid part of a node,
	 * the username should be escaped to "Joe\20Smith" before being made into a
	 * JID (e.g. "joe\20smith@example.com" after case-folding, etc. has been
	 * applied).
	 * <p>
	 * 
	 * All node escaping and un-escaping must be performed manually at the
	 * appropriate time; the JID class will not escape or un-escape
	 * automatically.
	 * 
	 * @param node
	 *            the node.
	 * @return the escaped version of the node.
	 */
	public static String escapeNode(String node) {
		if (node == null) {
			return null;
		}
		StringBuilder buf = new StringBuilder(node.length() + 8);
		for (int i = 0, n = node.length(); i < n; i++) {
			char c = node.charAt(i);
			switch (c) {
			case '"':
				buf.append("\\22");
				break;
			case '&':
				buf.append("\\26");
				break;
			case '\'':
				buf.append("\\27");
				break;
			case '/':
				buf.append("\\2f");
				break;
			case ':':
				buf.append("\\3a");
				break;
			case '<':
				buf.append("\\3c");
				break;
			case '>':
				buf.append("\\3e");
				break;
			case '@':
				buf.append("\\40");
				break;
			// 由于本系统中目前只对loginame中的'@'用来编码，所以不对'\'做编码处理，可以防止该方法被重复调用后产生的编码混乱
			// case '\\': buf.append("\\5c"); break;
			default: {
				if (Character.isWhitespace(c)) {
					buf.append("\\20");
				} else {
					buf.append(c);
				}
			}
			}
		}
		return buf.toString();
	}

	/**
	 * 把类似“squall\40kingsh”转义成“squall@kingsh”
	 * 
	 * @param node
	 *            the escaped version of the node.
	 * @return the un-escaped version of the node.
	 */
	public static String unescapeNode(String node) {
		if (node == null) {
			return null;
		}
		char[] nodeChars = node.toCharArray();
		StringBuilder buf = new StringBuilder(nodeChars.length);
		for (int i = 0, n = nodeChars.length; i < n; i++) {
			compare: {
				char c = node.charAt(i);
				if (c == '\\' && i + 2 < n) {
					char c2 = nodeChars[i + 1];
					char c3 = nodeChars[i + 2];
					if (c2 == '2') {
						switch (c3) {
						case '0':
							buf.append(' ');
							i += 2;
							break compare;
						case '2':
							buf.append('"');
							i += 2;
							break compare;
						case '6':
							buf.append('&');
							i += 2;
							break compare;
						case '7':
							buf.append('\'');
							i += 2;
							break compare;
						case 'f':
							buf.append('/');
							i += 2;
							break compare;
						}
					} else if (c2 == '3') {
						switch (c3) {
						case 'a':
							buf.append(':');
							i += 2;
							break compare;
						case 'c':
							buf.append('<');
							i += 2;
							break compare;
						case 'e':
							buf.append('>');
							i += 2;
							break compare;
						}
					} else if (c2 == '4') {
						if (c3 == '0') {
							buf.append("@");
							i += 2;
							break compare;
						}
					} else if (c2 == '5') {
						if (c3 == 'c') {
							buf.append("\\");
							i += 2;
							break compare;
						}
					}
				}
				buf.append(c);
			}
		}
		return buf.toString();
	}

	/** 把一个反斜杠处理出成两个反斜杠的方法，一般用来处理纯sql语句中的反斜杠字符 */
	public static String doubleBackslash(String node) {
		if (node == null) {
			return null;
		}
		StringBuilder buf = new StringBuilder(node.length() + 8);
		for (int i = 0, n = node.length(); i < n; i++) {
			char c = node.charAt(i);
			if (c == '\\') {
				buf.append("\\\\");
			} else {
				buf.append(c);
			}
		}
		return buf.toString();
	}

	/** 根据用户名取得ob号（用户名包括loginName和userName） */
	public static String getObNameByUser(String name) {
		String loginName = unescapeNode(name);
		if (loginName != null && loginName.length() > 0) {
			int i = loginName.indexOf("@");
			if (i > 0 && i != loginName.length() - 1) {
				// 用户名包含@，并且不在第一和最后一个
				return loginName.substring(i + 1, loginName.length());
			}
		}
		return null;
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param str
	 *            待转换编码的字符串
	 * @param oldCharset
	 *            原编码
	 * @param newCharset
	 *            目标编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String changeCharset(String str, String oldCharset,
			String newCharset) throws UnsupportedEncodingException {
		if (str != null) {
			// 用旧的字符编码解码字符串。解码可能会出现异常。
			byte[] bs = str.getBytes(oldCharset);
			// 用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * Pseudo-random number generator object for use with randomString(). The
	 * Random class is not considered to be cryptographically secure, so only
	 * use these random Strings for low to medium security applications.
	 */
	private static Random randGen = new Random();

	/**
	 * Array of numbers and letters of mixed case. Numbers appear in the list
	 * twice so that there is a more equal chance that a number will be picked.
	 * We can use the array to get a random number or letter by picking a random
	 * array index.
	 */
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	/**
	 * Returns a random String of numbers and letters (lower and upper case) of
	 * the specified length. The method uses the Random class that is built-in
	 * to Java which is suitable for low to medium grade security uses. This
	 * means that the output is only pseudo random, i.e., each number is
	 * mathematically generated so is not truly random.
	 * <p>
	 * <p/>
	 * The specified length must be at least one. If not, the method will return
	 * null.
	 * 
	 * @param length
	 *            the desired length of the random String to return.
	 * @return a random String of numbers and letters of the specified length.
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	/**
	 * unicodetoString
	 * 
	 * @param s
	 * @return
	 */
	public static final String unicodeToString(String s) {
		if (s == null || " ".equalsIgnoreCase(s.trim()))
			return " ";
		StringBuffer sb = new StringBuffer();
		boolean escape = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\':
			case '%':
				escape = true;
				break;
			case 'u':
			case 'U':
				if (escape) {
					try {
						sb.append((char) Integer.parseInt(s.substring(i + 1,
								i + 5), 16));
						escape = false;
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException();
					}
					i += 4;
				} else {
					sb.append(c);
				}
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * utf-8 转换成 unicode
	 * @param inStr
	 * @return
	 */
	public static String utf8ToUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				// 英文及数字等
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				// 全角半角字符
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				// 汉字
				short s = (short) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String resAsStr = "字符串转成UTF-8格式";
		System.out.print(TextUtil.utf8ToUnicode(resAsStr));
	}

}
