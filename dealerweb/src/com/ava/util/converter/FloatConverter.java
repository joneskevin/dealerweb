package com.ava.util.converter;

import org.springframework.core.convert.converter.Converter;

import com.ava.util.TypeConverter;

public class FloatConverter implements Converter<String, Float> {
	@Override
	public Float convert(String arg0) {
		return TypeConverter.toFloat(arg0);
	}
}
