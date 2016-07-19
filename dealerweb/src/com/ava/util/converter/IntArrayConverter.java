package com.ava.util.converter;

import org.springframework.core.convert.converter.Converter;

import com.ava.util.TypeConverter;

public class IntArrayConverter implements Converter<String[], int[]> {
	@Override
	public int[] convert(String[] arg0) {
		return TypeConverter.toInts(arg0);
	}
}
