package org.tsir.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StringUtilsVP {

	private StringUtilsVP() {
	}

	public static <T extends Number> List<T> splitStringToType(String value, String delimiter, Class<T> clazz) {
		String[] array = value.replaceAll("\\s", "").split(delimiter);
		return Arrays.stream(array, 0, array.length).map(s -> getObject(s, clazz)).collect(Collectors.toList());
	}

	private static <T> T getObject(String value, Class<T> t) {
		Objects.requireNonNull(value, "Se requiere un valor para la conversion");
		Objects.requireNonNull(t, "Se requiere un destino de conversion");
		value = value.trim();
		try {
			if (t.isAssignableFrom(Integer.class)) {
				return t.cast(Integer.parseInt(value));
			}
			if (t.isAssignableFrom(Long.class)) {
				return t.cast(Long.parseLong(value));
			}
			if (t.isAssignableFrom(Short.class)) {
				return t.cast(Short.parseShort(value));
			}
			if (t.isAssignableFrom(Boolean.class)) {
				return t.cast(Boolean.parseBoolean(value));
			}
		} catch (RuntimeException e) {
		}
		return t.cast(value);

	}

}
