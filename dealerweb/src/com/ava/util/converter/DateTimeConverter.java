package com.ava.util.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.ava.util.DateTime;

public class DateTimeConverter implements Converter<String, Date> {
	@Override
	public Date convert(String arg0) {
		Date d = DateTime.toDate(arg0);
		return d;
	}
}
