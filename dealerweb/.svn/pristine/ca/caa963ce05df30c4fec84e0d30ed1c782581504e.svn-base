package com.ava.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.sun.corba.se.spi.copyobject.CopierManager;

/***
 * @author xp.wang 时间类型定义：<br/>
 *         1、normalDate："yyyy-MM-dd"，String<br/>
 *         2、normalDateTime:"yyyy-MM-dd HH:mm:ss"，String<br/>
 *         2、millis： 毫秒格式，Long<br/>
 *         3、shortDate： 20120602，Integer<br/>
 *         4、shortDateTime： 20120602101203，Long<br/>
 *         5、date： Date对象<br/>
 */
public class DateTime {

	private final static Logger logger = Logger.getLogger(DateTime.class);
	
	/** yyyy */
	public final static String PATTERN_YEAR = "yyyy";
	
	/** yyyyMM */
	public final static String PATTERN_SHORTMONTH = "yyyyMM";
	/** yyyy-MM */
	public final static String PATTERN_YEAR_MONTH = "yyyy-MM";
	/** yyyy-MM-dd */
	public final static String PATTERN_DATE = "yyyy-MM-dd";
	/** yyyyMMdd */
	public final static String PATTERN_SHORTDATE = "yyyyMMdd";
	/** yyyy-MM-dd HH:mm:ss */
	public final static String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss.f */
	public final static String PATTERN_DATETIME_DOT = "yyyy-MM-dd HH:mm:ss.S";
	/** yyyyMMddHHmmss */
	public final static String PATTERN_SHORTDATETIME = "yyyyMMddHHmmss";
	/** yyyy-MM-dd HH:mm */
	public final static String PATTERN_DIVISION = "yyyy-MM-dd HH:mm";

	/** HH:mm:ss */
	// private static String PATTERN_TIME = "HH:mm:ss";

	/** can't Construct */
	private DateTime() {
	}

	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static Timestamp toTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * 返回Date对象,yyyyMM格式,yyyyMMdd格式，yyyyMMddHHmmss格式，yyyy-MM格式，yyyy-MM-dd格式，yyyy
	 * -MM-dd HH:mm:ss格式
	 */
	public static Timestamp toTimestamp(String dateTime) {
		return toTimestamp(toDate(dateTime));
	}

	/** 返回yyyy-MM-dd格式的字符串 */
	public static String getNormalDate() {
		return toNormalDate(new java.util.Date());
	}

	/** 返回yyyy-MM-dd HH:mm:ss格式的字符串 */
	public static String getNormalDateTime() {
		return toNormalDateTime(new java.util.Date());
	}

	/** 返回yyyy-MM-dd HH:mm格式的字符串 */
	public static String getDivisionDateTime() {
		return toDivisionDateTime(new java.util.Date());
	}

	/** 返回yyyyMMdd格式的字符串 */
	public static String getShortDate() {
		return toShortDate(new java.util.Date());
	}

	/** 返回yyyyMMddHHmmss格式的字符串 */
	public static String getShortDateTime() {
		return toShortDateTime(new java.util.Date());
	}

	/** 返回yyyyMMddHHmmss格式的长整型 */
	public static Long getShortDateTimeL() {
		return toShortDateTimeL(new java.util.Date());
	}

	/** 返回yyyyMM格式 */
	public static Integer getYearMonth() {
		String yearMonth = getYear() + getMonth();
		return TypeConverter.toInteger(yearMonth);
	}

	/** 返回yyyy格式字符串 */
	public static String getYear() {
		return getYear(new Date());
	}

	/** 返回yyyy格式 字符串 */
	public static String getYear(Date date) {
		if (date == null) {
			return null;
		}
		return TypeConverter.toString(date.getYear() + 1900);// Date类年份是从1900年算的
	}

	/** 返回MM格式字符串 */
	public static String getMonth() {
		return getNormalDate().substring(5, 7);
	}
	
	/** 返回MM格式字符串 */
	public static String getMonth(String datestr) {
		if (datestr == null) {
			return null;
		}
		return datestr.substring(5, 7);
	}

	/** 返回MM格式的整型 */
	public static Integer getMonthInt() {
		return TypeConverter.toInteger(getMonth());
	}

	/** 返回dd格式的字符串 */
	public static String getDay(String datestr) {
		if (datestr == null) {
			return null;
		}
		return datestr.substring(8, 10);
	}

	/** 返回dd格式的字符串 */
	public static String getDay() {
		return getDay(getNormalDate());
	}

	/**
	 * 返回Date对象,yyyyMM格式,yyyyMMdd格式，yyyyMMddHHmmss格式，yyyy-MM格式，yyyy-MM-dd格式，yyyy
	 * -MM-dd HH:mm:ss格式，yyyy-MM-dd HH:mm:ss.f格式
	 */
	public static Date toDate(String dateTime) {
		if (dateTime == null) {
			return null;
		} else {
			dateTime.trim();
		}
		try {
			Date d = null;
			if (dateTime.length() == 6) {
				d = DateUtils.parseDate(dateTime,
						new String[] { PATTERN_SHORTMONTH });
			} else if (dateTime.length() == 7) {
				d = DateUtils.parseDate(dateTime,
						new String[] { PATTERN_YEAR_MONTH });
			} else if (dateTime.length() == 8) {
				d = DateUtils.parseDate(dateTime,
						new String[] { PATTERN_SHORTDATE });
			} else if (dateTime.length() == 10) {
				d = DateUtils
						.parseDate(dateTime, new String[] { PATTERN_DATE });
			} else if (dateTime.length() == 14) {
				d = DateUtils.parseDate(dateTime,
						new String[] { PATTERN_SHORTDATETIME });
			} else if (dateTime.length() == 19) {
				d = DateUtils.parseDate(dateTime,
						new String[] { PATTERN_DATETIME });
			} else if (dateTime.length() == 21) {
				d = DateUtils.parseDate(dateTime,
						new String[] { PATTERN_DATETIME_DOT });
			} else if (dateTime.length() == 28) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
				try {
					d = sdf.parse(dateTime);
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}
			return d;
		} catch (ParseException e) {
			logger.warn("toDate(String dateTime) error...");
			e.printStackTrace();
		}
		return null;
	}

	/** 返回Date对象,可以输入yyyyMMdd格式和yyyyMMddHHmmss格式 */
	public static Date toDate(Long dateTime) {
		if (dateTime == null) {
			return null;
		}
		String dateStr = String.valueOf(dateTime);
		Date d = toDate(dateStr);
		return d;
	}

	/** 返回yyyy-MM-dd格式的字符串 */
	public static String toNormalDate(Integer shortDate) {
		if (shortDate == null || shortDate.intValue() == 0) {
			return null;
		}
		try {
			Date d = DateUtils.parseDate(shortDate.toString(),
					new String[] { "yyyyMMdd" });
			return DateFormatUtils.format(d, PATTERN_DATE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 返回yyyy-MM-dd格式的字符串 */
	public static String toNormalDate(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATE);
	}

	/** 返回yyyy-MM-dd格式的字符串 */
	public static String toNormalDate(Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATE);
	}

	/** 返回yyyy-MM-dd HH:mm:ss格式的字符串 */
	public static String toNormalDateTime(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATETIME);
	}
	
	/** 返回yyyy-MM-dd HH:mm:ss.f格式的字符串 */
	public static String toNormalDotDateTime(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATETIME_DOT);
	}

	/** 返回yyyy-MM-dd HH:mm格式的字符串 */
	public static String toDivisionDateTime(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DIVISION);
	}

	/** 返回yyyy-MM-dd HH:mm:ss格式的字符串 */
	public static String toNormalDateTime(Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATETIME);
	}

	/** 返回yyyy-MM-dd HH:mm:ss格式的字符串,可以输入yyyyMMdd格式和yyyyMMddHHmmss格式 */
	public static String toNormalDateTime(Long dateTime) {
		if (dateTime == null) {
			return null;
		}
		Date d = toDate(dateTime);
		if (d == null) {
			return null;
		}
		return DateFormatUtils.format(d, PATTERN_DATETIME);
	}

	/** 返回yyyyMMdd格式的字符串 */
	public static String toShortDate(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATE);
	}

	/** 返回yyyyMMdd格式的字符串 */
	public static String toShortDate(Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATE);
	}

	/** 返回yyyyMMdd格式的整型,可以输入yyyy-MM-dd格式和yyyy-MM-dd HH:mm:ss格式 */
	public static Integer toShortDate(String dateTime) {
		Date d = toDate(dateTime);
		return TypeConverter.toInteger(toShortDate(d));
	}
	
	/** 返回yyyyMM格式的字符串 */
	public static String toShortStr(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTMONTH);
	}

	/** 返回yyyyMMddHHmmss格式的字符串 */
	public static String toShortDateTime(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATETIME);
	}

	/** 返回yyyyMMddHHmmss格式的字符串 */
	public static String toShortDateTime(Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATETIME);
	}

	/** 返回yyyyMMddHHmmss格式的长整型 */
	public static Long toShortDateTimeL(Date dateTime) {
		String dateStr = toShortDateTime(dateTime);
		if (dateTime == null) {
			return null;
		}
		return Long.valueOf(dateStr);
	}

	/** 返回yyyyMMddHHmmss格式的长整型 */
	public static Long toShortDateTimeL(Timestamp dateTime) {
		String dateStr = toShortDateTime(dateTime);
		if (dateTime == null) {
			return null;
		}
		return Long.valueOf(dateStr);
	}

	/** 返回yyyyMMddHHmmss格式的长整型 ,可以输入毫秒格式时间 */
	public static Long toShortDateTimeL(Long millis) {
		if (millis == null) {
			return null;
		}
		return toShortDateTimeL(new Date(millis));
	}

	/**
	 * 返回yyyyMMddHHmmss格式的长整型,可以输入毫秒格式时间，yyyyMMdd格式，yyyyMMddHHmmss格式，yyyy-MM-
	 * dd格式，yyyy-MM-dd HH:mm:ss格式
	 */
	public static Long toShortDateTimeL(String dateTime) {
		return toShortDateTimeL(toDate(dateTime));
	}

	/**
	 * @param Long
	 *            shortFormatTime yyyyMMdd格式或yyyyMMddHHmmss格式
	 * @return 毫秒数
	 */
	public static Long toMillisFromShort(Long shortFormatTime) {
		Date time = toDate(shortFormatTime);
		if (time == null) {
			return null;
		}
		return time.getTime();
	}

	/** 返回当前时间是一周中的第几天，从周日到周六分别是1-7 */
	public static String getDayOfWeek() {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String dayofweek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		return dayofweek;
	}

	/** 返回当前时间是一年中的第几周，若未指定时间，则默认为当前时间，从1-52周 */
	public static Integer getCurrentWeekOfYear() {
		return getWeekOfYear(new Date());
	}

	/** 返回给定时间是一周中的第几周，若未指定时间，则默认为当前时间，从1-52周 */
	public static Integer getWeekOfYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		if (null != date) cal.setTime(date);
		Integer weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		
		if(cal.get(Calendar.DAY_OF_WEEK) == 1) weekOfYear --;
			
		return weekOfYear;
	}

	/** 获取指定年份的最大周数 */
	public static Integer getMaxWeekOfYear(final int year) {
		int maxWeek = 52;
		int tempWeek = 52;
		String strDate = year + "-12" + "-";
		for (int i = 31; i >= 31 - 7; i--) {// 从12月31号向前寻找一个星期所在周序号，最大的序号，即为本年的最大周（最多不超过53周）
			Date date = toDate(strDate + i);
			tempWeek = getWeekOfYear(date);
			if (tempWeek > maxWeek) {
				maxWeek = 53;
				break;
			}
		}
		return maxWeek;
	}

	/**
	 * 获取指定日期的起始时间，截至时间的String数组
	 * 
	 * @param day
	 *            格式(yyyyMMdd)
	 * @return 格式:{"yyyyMMddHHmmss", "yyyyMMddHHmmss"}
	 */
	public static String[] getRangeOfDay(String day) {
		if (day == null || day.length() != 8) {
			return null;
		}

		String[] range = new String[2];
		range[0] = day + "000000";

		// Date startDate = toDate(day + "000000");
		// String nextDate = toShortDate(getNextDate(startDate, 1));
		range[1] = day + "235959";

		return range;
	}

	/** 获取当年指定周数获取该周的起始日期，截至日期的String数组,格式(yyyyMMddHHmmss)** */
	public static String[] getRangeOfWeek(int week) {
		int year = TypeConverter.toInteger(getYear()).intValue();
		return getRangeOfWeek(year, week);
	}

	/** 获取指定年份，和年周数获取该周的起始日期，截至日期的String数组,格式(yyyyMMdd) ***/
	public static String[] getRangeOfWeek(int year, int week) {
		String[] dateRange = new String[2];
		Calendar calFirstDayOfTheYear = new GregorianCalendar(year,
				Calendar.JANUARY, 1);
		calFirstDayOfTheYear.add(Calendar.DATE, 7 * (week - 1));

		int dayOfWeek = calFirstDayOfTheYear.get(Calendar.DAY_OF_WEEK) - 1; // 转换为周一到星期天的范围模式（默认是周日到周一）
		Calendar calFirstDayInWeek = (Calendar) calFirstDayOfTheYear.clone();
		calFirstDayInWeek.add(Calendar.DATE,
				calFirstDayOfTheYear.getActualMinimum(Calendar.DAY_OF_WEEK)
						- dayOfWeek);
		Date firstDayInWeek = calFirstDayInWeek.getTime();

		Calendar calLastDayInWeek = (Calendar) calFirstDayOfTheYear.clone();
		calLastDayInWeek.add(Calendar.DATE,
				calFirstDayOfTheYear.getActualMaximum(Calendar.DAY_OF_WEEK)
						- dayOfWeek);
		Date lastDayInWeek = calLastDayInWeek.getTime();

		dateRange[0] = DateFormatUtils
				.format(firstDayInWeek, PATTERN_SHORTDATE);
		dateRange[1] = DateFormatUtils.format(lastDayInWeek, PATTERN_SHORTDATE);

		// System.out.println(year + "年第" + week + "个礼拜的第一天是" + dateRange[0]);
		// System.out.println(year + "年第" + week + "个礼拜的第七天是" + dateRange[1]);
		return dateRange;
	}
	
	/** 获取指定年份，和年周数获取该周的起始日期，截至日期的String数组,格式(yyyy-MM-dd) ***/
	public static String[] getRangeOfWeek(String year, String week) {
		String[] dateRange = new String[2];
		Calendar calFirstDayOfTheYear = new GregorianCalendar(Integer.parseInt(year),
				Calendar.JANUARY, 1);
		calFirstDayOfTheYear.add(Calendar.DATE, 7 * (Integer.parseInt(week) - 1));

		int dayOfWeek = calFirstDayOfTheYear.get(Calendar.DAY_OF_WEEK) - 1; // 转换为周一到星期天的范围模式（默认是周日到周一）
		Calendar calFirstDayInWeek = (Calendar) calFirstDayOfTheYear.clone();
		calFirstDayInWeek.add(Calendar.DATE,
				calFirstDayOfTheYear.getActualMinimum(Calendar.DAY_OF_WEEK)
						- dayOfWeek);
		Date firstDayInWeek = calFirstDayInWeek.getTime();

		Calendar calLastDayInWeek = (Calendar) calFirstDayOfTheYear.clone();
		calLastDayInWeek.add(Calendar.DATE,
				calFirstDayOfTheYear.getActualMaximum(Calendar.DAY_OF_WEEK)
						- dayOfWeek);
		Date lastDayInWeek = calLastDayInWeek.getTime();

		dateRange[0] = DateFormatUtils
				.format(firstDayInWeek, PATTERN_DATE);
		dateRange[1] = DateFormatUtils.format(lastDayInWeek, PATTERN_DATE);

		// System.out.println(year + "年第" + week + "个礼拜的第一天是" + dateRange[0]);
		// System.out.println(year + "年第" + week + "个礼拜的第七天是" + dateRange[1]);
		return dateRange;
	}
	public static String[] getRangeOfMonth(int year, int month) {
		String[] dateRange = new String[2];
		if(month<=9){
			dateRange[0] = getMonthFirstDay(toDate(year+"-0"+month+"-01"), PATTERN_DATE);
			dateRange[1] = getMonthLastDay(toDate(year+"-0"+month+"-01"), PATTERN_DATE) ;
		}else{
			dateRange[0] = getMonthFirstDay(toDate(year+"-"+month+"-01"), PATTERN_DATE);
			dateRange[1] = getMonthLastDay(toDate(year+"-"+month+"-01"), PATTERN_DATE) ;
		}
		return dateRange;
	}
	
	public static String[] getRangeOfQuarter(int year, int quarter) {
		String[] dateRange = new String[2];
		if(quarter==1){
			dateRange[0] = year+"-01-01";
			dateRange[1] = year+"-03-31";
		}else if(quarter==1){
			dateRange[0] = year+"-04-01";
			dateRange[1] = year+"-06-30";
		}else if(quarter==1){
			dateRange[0] = year+"-07-01";
			dateRange[1] = year+"-09-30";
		}else if(quarter==1){
			dateRange[0] = year+"-10-01";
			dateRange[1] = year+"-12-31";
		}
		return dateRange;
	}

	/**
	 * 获取指定日期所在月的起始日期，截至日期的String数组
	 * 
	 * @return {20130201000000, 20130228235959}
	 */
	public static String[] getRangeOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		String[] dateRange = new String[2];
		dateRange[0] = getMonthFirstDay(date, PATTERN_SHORTDATE) + "000000";
		dateRange[1] = getMonthLastDay(date, PATTERN_SHORTDATE) + "235959";
		return dateRange;
	}

	/**
	 * 得到本月的第一天
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMonthFirstDay(Date date) {
		return getMonthFirstDay(date, null);
	}

	/**
	 * 得到本月的第一天
	 * 
	 * @return pattern格式的字符串
	 */
	public static String getMonthFirstDay(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (MyStringUtils.isBlank(pattern)) {
			pattern = PATTERN_DATE;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(calendar.getTime(), pattern);
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMonthLastDay(Date date) {
		return getMonthLastDay(date, null);
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return pattern格式的字符串
	 */
	public static String getMonthLastDay(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (MyStringUtils.isBlank(pattern)) {
			pattern = PATTERN_DATE;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(calendar.getTime(), pattern);
	}

	/**
	 * 计算毫秒类型的时间距离现在的分钟差
	 * 
	 * @param millisTime
	 * @return 小于0：比现在早
	 */
	public static long getMinuteDifferenceToNow(Long millisTime) {
		if (millisTime == null) {
			return -1;
		}
		long currentMillis = System.currentTimeMillis();
		int diff = new Long((millisTime - currentMillis) / 1000l / 60)
				.intValue();
		return diff;
	}

	/**
	 * 计算两个Date类型的时间差，单位转换成秒
	 * 
	 * @param starttime
	 * @param endtime
	 * @return -1：时间参数有一个为null； 其他：endtime-starttime的时间差，单位是秒
	 */
	public static long getSecondDifference(Date starttime, Date endtime) {
		if (starttime == null || endtime == null) {
			return -1;
		}
		int diff = new Long((endtime.getTime() - starttime.getTime()) / 1000l)
				.intValue();
		return diff;
	}

	/**
	 * 计算两个Date类型的时间差，单位转换成秒
	 * 
	 * @param starttime
	 * @param endtime
	 * @return -1：时间参数有一个为null； 其他：endtime-starttime的时间差，单位是秒
	 */
	public static long getSecondDifferenceNew(Date starttime, Date endtime) {
		if (starttime == null || endtime == null) {
			return -1;
		}
		int diff = new Long((endtime.getTime() - starttime.getTime()) / 1000)
				.intValue();
		return diff;
	}
	
	/**
	 * 计算两个Date类型的时间差，单位转换成小时
	 * 
	 * @param starttime
	 * @param endtime
	 * @return -1：时间参数有一个为null； 其他：endtime-starttime的时间差，单位是秒
	 */
	public static long getHourDifference(Long starttime_short,
			Long endtime_short) {
		Date starttime = toDate(starttime_short);
		Date endtime = toDate(endtime_short);
		if (starttime == null || endtime == null) {
			return -1;
		}
		int diff = new Long(
				(endtime.getTime() - starttime.getTime()) / 1000l / 3600l)
				.intValue();
		return diff;
	}

	/**
	 * 得到两个String类型日期之间的天数差 示例：starttime="2008-12-03 15:21:03"
	 * endtime="2008-12-27 15:21:03" *
	 * */
	public static int getDaysInterval(String starttime, String endtime) {
		return getDaysInterval(toDate(starttime), toDate(endtime));
	}

	/**
	 * 得到两个Date类型日期之间的天数差 示例：starttime="2008-12-03 15:21:03"
	 * endtime="2008-12-27 15:21:03"
	 * */
	public static int getDaysInterval(Date starttime, Date endtime) {
		if (starttime == null || endtime == null) {
			return -1;
		}
		return new Long((endtime.getTime() - starttime.getTime()) / 86400000)
				.intValue();
	}

	/**
	 * 根据TimeDurationFormat.Format定义的格式来格式化时间数据，例如：“XX天XX小时XX分钟”、“hh:mm:ss”等
	 * 
	 * @param time
	 *            时间，单位（秒）
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String getTimeInterval(long time,
			TimeDurationFormat.Format format) {
		TimeDurationFormat timeFormat = new TimeDurationFormat(format);
		return timeFormat.format(time);
	}

	/**
	 * 获取两个日期(字符串)之间的时间差，显示格式根据TimeDurationFormat.Format对象定义的格式显示
	 * 
	 * @param starttime
	 *            开始日期字符串
	 * @param endtime
	 *            结束日期字符串
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String getTimeInterval(String starttime, String endtime,
			TimeDurationFormat.Format format) {
		return getTimeInterval(toDate(starttime), toDate(endtime), format);
	}

	/**
	 * 获取两个日期之间的时间差，显示格式根据TimeDurationFormat.Format对象定义的格式显示
	 * 
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param format
	 *            时间格式
	 * @return String
	 */
	public static String getTimeInterval(Date starttime, Date endtime,
			TimeDurationFormat.Format format) {
		TimeDurationFormat timeFormat = new TimeDurationFormat(format);
		return timeFormat.format(starttime, endtime);
	}

	/** 计算出给定日期后n年的时间 */
	public static Date addYears(Date starttime, Integer years) {
		if (starttime == null || years == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)
				+ years);
		return calendar.getTime();
	}
	
	/** 得到给定日期n天前的日期 
	 *  days正数向前，负数向后
	*/
	public static Date minusDays(Date starttime, Integer days) {
		if (starttime == null || days == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)
				- days);
		return calendar.getTime();
	}

	/** 得到给定日期n小时前的时间 */
	public static Date minusHours(Date starttime, Integer hours) {
		if (starttime == null || hours == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
				- hours);
		return calendar.getTime();
	}

	/** 计算出给定日期后n天的时间 */
	public static Date addDays(Date starttime, Integer days) {
		if (starttime == null || days == null) {
			return null;
		}
		// return new Date(new Long(starttime.getTime() + 86400000L*days)) ;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}
	
	public static Date getNextDays(Date starttime, Integer days) {
		if (starttime == null || days == null) {
			return null;
		}
		// return new Date(new Long(starttime.getTime() + 86400000L*days)) ;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/** 计算出给定时间加上n秒的时间 */
	public static Date addSeconds(Date starttime, Integer seconds) {
		if (starttime == null || seconds == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	/** 计算出给定时间n小时后的时间 */
	public static Date addHours(Date starttime, Integer hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.add(Calendar.HOUR_OF_DAY, hours.intValue());
		return calendar.getTime();
	}

	/**
	 * 得到历史时间和当前时间的间隔，根据时间长短返回不同的描述 比如：12分钟前；5小时前；2天前；3个月前
	 */
	public static String getIntervalHistoryToNow(Date historyTime) {
		if (historyTime == null) {
			return null;
		}
		long secondDifference = getSecondDifference(historyTime,
				new java.util.Date());
		if (secondDifference <= 60) {
			// 如果时间在一分钟内，则按秒计算
			return secondDifference + "秒前";
		} else if (secondDifference <= 3600) {
			// 如果时间在一小时内，则按分钟计算
			return (secondDifference / 60 + 1) + "分钟前";
		} else if (secondDifference <= 3600 * 24) {
			// 如果在一天内，则按小时计算
			return (secondDifference / 3600) + "小时前";
		} else if (secondDifference <= 3600 * 24 * 31) {
			// 如果在一个月内，则按天计算
			return (secondDifference / (3600 * 24)) + "天前";
		} else {
			// 剩余的都按月计算
			return (secondDifference / (3600 * 24 * 30)) + "个月前";
		}
	}

	/**
	 * 得到未来时间和当前时间的间隔，根据时间长短返回不同的描述 比如：12分钟；5小时；2天；3个月
	 */
	public static String getIntervalFutureToNow(Date futureTime) {
		if (futureTime == null) {
			return null;
		}
		long secondDifference = getSecondDifference(new java.util.Date(),
				futureTime);
		if (secondDifference <= 3600) {
			// 如果时间在一小时内，则按分钟计算
			return (secondDifference / 60 + 1) + "分钟";
		} else if (secondDifference <= 3600 * 24) {
			// 如果在一天内，则按小时计算
			return (secondDifference / 3600 + 1) + "小时";
		} else if (secondDifference <= 3600 * 24 * 31) {
			// 如果在一个月内，则按天计算
			return (secondDifference / (3600 * 24) + 1) + "天";
		} else {
			// 剩余的都按月计算
			return (secondDifference / (3600 * 24 * 30) + 1) + "个月";
		}
	}

	/** 得到两个Date类型日期之间的月份差 */
	public static int getMonthInterval(Date starttime, Date endtime) {
		if (starttime == null || endtime == null) {
			return 0;
		}
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(starttime);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endtime);
		return (endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR)) * 12
				+ (endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH));
	}

	/** 得到两个Date类型日期之间的年份差 */
	public static int getYearInterval(Date starttime, Date endtime) {
		if (starttime == null || endtime == null) {
			return 0;
		}
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(starttime);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endtime);
		return endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
	}

	/**
	 * 初始化最近天数，放到list中
	 * 
	 * @param days
	 *            初始化天数
	 * @return
	 */
	public static List<String> initDayList(int days) {
		List<String> dayList = new ArrayList<String>();
		for (int i = days - 1; i >= 0; i--) {
			String day = toNormalDate(DateTime.minusDays(new Date(), i));
			dayList.add(day);
		}

		return dayList;
	}

	/**
	 * 初始化一个时间段的天数，放到list中
	 * 
	 * @param startDate
	 *            起始时间，字符串：“2012-06-18”
	 * @param endDate
	 *            结束时间，字符串：“2012-06-20”
	 * @return
	 */
	public static List<String> initDayList(String startDate, String endDate) {
		Date sDate = toDate(startDate);
		Date eDate = toDate(endDate);
		return initDayList(sDate, eDate);
	}

	/**
	 * 初始化一个时间段的天数，放到list中
	 * 
	 * @param startDate
	 *            起始时间，Date对象
	 * @param endDate
	 *            结束时间，Date对象
	 * @return
	 */
	public static List<String> initDayList(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return null;
		List<String> dayList = new ArrayList<String>();
		int interval = getDaysInterval(startDate, endDate) + 1;
		for (int i = 0; i < interval; i++) {
			Date currentDate = addDays(startDate, i);
			dayList.add(toNormalDate(currentDate));
		}

		return dayList;
	}
	
	/**
	 * 初始化一个时间段的天数，放到list中
	 * 
	 * @param startDate
	 *            起始时间，Date对象
	 * @param endDate
	 *            结束时间，Date对象
	 * @return
	 */
	public static List<Date> initDateList(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return null;
		List<Date> dayList = new ArrayList<Date>();
		int interval = getDaysInterval(startDate, endDate) + 1;
		for (int i = 0; i < interval; i++) {
			Date currentDate = addDays(startDate, i);
			dayList.add(currentDate);
		}
		
		return dayList;
	}

	/**
	 * 根据Date日期，返回该日期所在的月份的第一天
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		if (date == null)
			throw new IllegalArgumentException("date can't be null");

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	/**
	 * 根据Date日期，返回该日期所在的月份的最后一天
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		if (date == null)
			throw new IllegalArgumentException("date can't be null");

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * 判断两个日期是否为同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		String strDate1 = toShortDate(date1);
		String strDate2 = toShortDate(date2);

		if (strDate1 == null || strDate2 == null) {
			throw new IllegalArgumentException("时间参数为空");
		}
		if (strDate1.equals(strDate2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断一个日期是上午还是下午
	 * 
	 * @param date
	 * @return am,pm
	 */
	public static String toAmOrPm(Date date) {
		String dataTime = toShortDateTime(date);
		if (dataTime == null) {
			throw new IllegalArgumentException("时间参数为空");
		}

		int hour = TypeConverter.toInteger(dataTime.substring(8, 10));
		if (hour >= 12) {
			return "pm";
		} else {
			return "am";
		}
	}

	/**
	 * 判断一个日期是白天还是夜晚
	 * 
	 * @param date
	 * @return day,night
	 */
	public static String toDayOrNight(Date date) {
		String dataTime = toShortDateTime(date);
		if (dataTime == null) {
			throw new IllegalArgumentException("时间参数为空");
		}

		int hour = TypeConverter.toInteger(dataTime.substring(8, 10));
		if (hour >= 6 && hour <= 23) {
			return "day";
		} else {
			return "night";
		}
	}

	/**
	 * 是否包含当天
	 * 
	 * @param startDate
	 *            格式：20120712
	 * @param endDate
	 *            格式：20120712
	 * @return
	 */
	public static boolean hasCurrentDay(int startDate, int endDate) {
		int currentDate = TypeConverter.toInteger(getShortDate());
		if (currentDate >= startDate && currentDate <= endDate) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否包含当天
	 * 
	 * @param dates
	 *            格式：20121105
	 * @return
	 */
	public static boolean hasCurrentDay(Integer[] dates) {
		if (dates == null)
			return false;
		int currentDate = TypeConverter.toInteger(getShortDate());
		for (Integer date : dates) {
			if (date != null && date.intValue() == currentDate)
				return true;
		}

		return false;
	}

	/**
	 * 判断是否包含当前月
	 * 
	 * @param startMonth
	 *            开始月份
	 * @param endMonth
	 *            结束月份
	 * @return
	 */
	public static boolean hasCurrentMonth(int startMonth, int endMonth) {
		int currentMonth = getYearMonth();

		if (currentMonth >= startMonth && currentMonth <= endMonth) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否包含当前月
	 * 
	 * @param months
	 *            月份集合，格式：201302
	 * @return
	 */
	public static boolean hasCurrentMonth(Integer[] months) {
		if (months == null)
			return false;

		int currentMonth = getYearMonth();
		for (Integer month : months) {
			if (month != null && month.intValue() == currentMonth) {
				return true;
			}
		}

		return false;
	}
	
	public static Date getDate(String source,String pattern){
		Date date=null;
		try {
			date= new SimpleDateFormat(pattern).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String getDate(Date source,String pattern){
		String date=null;
		date= new SimpleDateFormat(pattern).format(source);
		return date;
	}
	
	public static String stringDateFormat(String sourceDate,String orignPattern,String newPattern){
		Date currentDate=new Date();
		if(null==sourceDate || "".equals(sourceDate)){
			sourceDate=getDate(currentDate,newPattern);
			return sourceDate;
		}else{
			Date orignDate=getDate(sourceDate,orignPattern);
			return getDate(orignDate,newPattern);
		}
	}
	
	/** 
     * 根据原来的时间（Date）获得相对偏移 N 月的时间（Date） 
     * @param protoDate 原来的时间（java.util.Date） 
     * @param dateOffset（向前移正数，向后移负数） 
     * @return 时间（java.util.Date） 
     */ 
    public static Date getOffsetMonthDate(Date protoDate,int monthOffset){
        Calendar cal = Calendar.getInstance();  
        cal.setTime(protoDate);  
//      cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthOffset);  //这种写法是错误的，这种偏移以30天为标准  
        cal.add(Calendar.MONTH, -monthOffset); //正确写法  
        return cal.getTime();  
    } 
	
	/*
	 * 获得今天的00:00:00 的date类型 daysOff是当前日期往前多少天
	 *  格式为 开始日 yyyy-MM-dd 00:00:00  到今天 yyyy-MM-dd 23:59:59
	 */
	 public static Date[] getSomeDateZone(int daysOff){
		Date[] date = new Date[2];
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
		SimpleDateFormat dateformat2=new SimpleDateFormat("yyyy-MM-dd 23:59:59"); 
		Calendar cal = Calendar.getInstance();
		Date endDate = cal.getTime(); 
		Date beginDate = DateTime.minusDays(endDate, daysOff); 
		endDate = DateTime.toDate(dateformat2.format(endDate));
		beginDate =DateTime.toDate(dateformat1.format(beginDate)); 
		date[0]=beginDate;
		date[1]=endDate;
		return date;
	 }
	 
 	/**
	 * 
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {
		int season = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			season = 1;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			season = 2;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			season = 3;
			break;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			season = 4;
			break;
		default:
			break;
		}
		return season;
	}
	 
    /**
     * 通过不同的月份，返回每月的开始日期和结束日期,此方法待优化
     * @param month
     * @return
     */
	public static String[] getMonthAarry(Integer month) {
		if (month != null) {
			String[] date = new String[2];
			//第一个月
	        Date startDate1 = DateTime.minusDays(new Date(), 365);
	        Date endDate1 = DateTime.minusDays(startDate1, -30);
	        
	        //第二个月
	        Date startDate2 = DateTime.minusDays(endDate1, -1);
	        Date endDate2 = DateTime.minusDays(startDate2, -30);
	        
	        //第三个月
	        Date startDate3 = DateTime.minusDays(endDate2, -1);
	        Date endDate3 = DateTime.minusDays(startDate3, -30);
	        
	        //第四个月
	        Date startDate4 = DateTime.minusDays(endDate3, -1);
	        Date endDate4 = DateTime.minusDays(startDate4, -30);
	        
	        //第无个月
	        Date startDate5 = DateTime.minusDays(endDate4, -1);
	        Date endDate5 = DateTime.minusDays(startDate5, -30);
	        
	        //第六个月
	        Date startDate6 = DateTime.minusDays(endDate5, -1);
	        Date endDate6 = DateTime.minusDays(startDate6, -30);
	        
	        //第七个月
	        Date startDate7 = DateTime.minusDays(endDate6, -1);
	        Date endDate7 = DateTime.minusDays(startDate7, -30);
	        
	        //第八个月
	        Date startDate8 = DateTime.minusDays(endDate7, -1);
	        Date endDate8 = DateTime.minusDays(startDate8, -30);
	        
	        //第九个月
	        Date startDate9 = DateTime.minusDays(endDate8, -1);
	        Date endDate9 = DateTime.minusDays(startDate9, -30);
	        
	        //第十个月
	        Date startDate10 = DateTime.minusDays(endDate9, -1);
	        Date endDate10 = DateTime.minusDays(startDate10, -30);
	        
	        //第十一个月
	        Date startDate11 = DateTime.minusDays(endDate10, -1);
	        Date endDate11 = DateTime.minusDays(startDate11, -30);
	        
	        //第十二个月
	        Date startDate12 = DateTime.minusDays(endDate11, -1);
	        Date endDate12 = DateTime.minusDays(startDate12, -24);
	        
			if (month == 1) {
				date[0] = DateTime.toNormalDate(startDate1);
				date[1] = DateTime.toNormalDate(endDate1);
			} 
			if (month == 2) {
				date[0] = DateTime.toNormalDate(startDate2);
				date[1] = DateTime.toNormalDate(endDate2);
			}
			if (month == 3) {
				date[0] = DateTime.toNormalDate(startDate3);
				date[1] = DateTime.toNormalDate(endDate3);
			}
			if (month == 4) {
				date[0] = DateTime.toNormalDate(startDate4);
				date[1] = DateTime.toNormalDate(endDate4);
			}
			if (month == 5) {
				date[0] = DateTime.toNormalDate(startDate5);
				date[1] = DateTime.toNormalDate(endDate5);
			}
			if (month == 6) {
				date[0] = DateTime.toNormalDate(startDate6);
				date[1] = DateTime.toNormalDate(endDate6);
			}
			if (month == 7) {
				date[0] = DateTime.toNormalDate(startDate7);
				date[1] = DateTime.toNormalDate(endDate7);
			}
			if (month == 8) {
				date[0] = DateTime.toNormalDate(startDate8);
				date[1] = DateTime.toNormalDate(endDate8);
			}
			if (month == 9) {
				date[0] = DateTime.toNormalDate(startDate9);
				date[1] = DateTime.toNormalDate(endDate9);
			}
			if (month == 10) {
				date[0] = DateTime.toNormalDate(startDate10);
				date[1] = DateTime.toNormalDate(endDate10);
			}
			if (month == 11) {
				date[0] = DateTime.toNormalDate(startDate11);
				date[1] = DateTime.toNormalDate(endDate11);
			}
			if (month == 12) {
				date[0] = DateTime.toNormalDate(startDate12);
				date[1] = DateTime.toNormalDate(endDate12);
			}
			return date;
		}
		
		return null;
	}
	
	/**
	 * 功能描述：返回小时
	 *
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.get(Calendar.HOUR_OF_DAY);
	}
	 
	/**
	 * 功能描述：返回分
	 *
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.get(Calendar.MINUTE);
	}
	 
	/**
	 * 返回秒钟
	 *
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.get(Calendar.SECOND);
	}
	 
	/**
	 * 功能描述：返回毫
	 *
	 * @param date
	 *            日期
	 * @return 返回毫
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.getTimeInMillis();
	}
	
	/**
	 * 将秒转换成格式为指定格式：X小时X分钟X秒
	 * @param second 秒
	 * @param type 0:X小时X分钟X秒 ,1:X小时X分钟
	 * @return
	 */
	public static String formatSecond(Integer second, Integer type){  
        String fomatDate = "0''";  
        if (type != null) {
        	if (type == 1) {
        		fomatDate = "0'"; 
        	}
        	 if(second!=null){  
                 String format = "";  
                 Object[] array = null;  
                 Integer hours = (int) (second/(3600));  
                 Integer minutes = (int) ((second%3600)/60);  
                 Integer seconds = (int) (second%60);
                 if (type == 0) {
                	 if(hours>0){  
                         format="%1$,d小时%2$,d'%3$,d''";  
                         array=new Object[]{hours,minutes,seconds};  
                     }else if(minutes>0){  
                         format="%1$,d'%2$,d''";  
                         array=new Object[]{minutes,seconds};  
                     }else{  
                         format="%1$,d''";  
                         array=new Object[]{seconds};  
                     }  
                 } else if (type == 1) {
                	 if(hours>0){  
         				format="%1$,d小时%2$,d分";  
         				array=new Object[]{hours,minutes};  
         			}else if(minutes>0){  
         				format="%1$,d分";  
         				array=new Object[]{minutes};  
         			}else if (seconds > 0){  
         				format="%1$,d分";  
         				minutes = 1;
         				array=new Object[]{minutes};  
         			} else {
         				format="%1$,d分";  
         				minutes = 0;
         				array=new Object[]{minutes}; 
         			} 
                 }
                 
                 fomatDate= String.format(format, array);  
             }  
        }
         
       return fomatDate;  
   }
	
	public static String getDateStr(String source,String pattern){
		String startDate = null;
		try {
			SimpleDateFormat dateFmt = new SimpleDateFormat(pattern); 
			startDate = dateFmt.format(dateFmt.parse(source));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;
	}
	


	public static void main(String[] args) {
	}
	
}