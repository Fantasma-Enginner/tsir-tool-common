package org.tsir.common.modules;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface Registrable {

	default List<Operation> getAuthorities() throws IllegalAccessException {
		List<Operation> authorities = new ArrayList<>();
		Field[] fields = getClass().getFields();
		for (Field field : fields) {
			String name = field.getName();
			if (name.endsWith("RESOURCE")) {
				authorities.add((Operation) field.get(null));
			}
		}
		return authorities;
	}

}
