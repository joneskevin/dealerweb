package com.ava.util.converter;

import org.springframework.core.convert.converter.Converter;

import com.ava.util.TypeConverter;

public class LongConverter implements Converter<String, Long> {
	@Override
	public Long convert(String arg0) {
		return TypeConverter.toLong(arg0);
	}
}
