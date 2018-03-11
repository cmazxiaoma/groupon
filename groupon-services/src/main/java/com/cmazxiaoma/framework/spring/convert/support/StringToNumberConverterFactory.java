package com.cmazxiaoma.framework.spring.convert.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.NumberUtils;

/**
 * StringToNumberConverterFactory
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

	public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToNumber<T>(targetType);
	}

	private static final class StringToNumber<T extends Number> implements Converter<String, T> {

		private final Class<T> targetType;

		public StringToNumber(Class<T> targetType) {
			this.targetType = targetType;
		}

		public T convert(String source) {
			if (source.length() == 0) {
				return null;
			}
			if ("on".equals(source)) {
				source = "1";
			}
			return NumberUtils.parseNumber(source, this.targetType);
		}
	}

}
