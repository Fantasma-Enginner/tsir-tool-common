package org.tsir.common.services.resources.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración de los tipos de recursos: * `DOMINIO` - Tipo de recurso que
 * agrupa los módulos por relación de funcionalidades de negocio. * `MODULO` -
 * Tipo de recursos que agrupa las operaciones destinadas a una cumplir una
 * particularidad o caracteristica de negocio. * `OPERACION` - Tipo de recurso
 * que representa una tarea puntual para el negocio.
 */
public enum ResourceType {
	DOMINIO("DOMINIO"), MODULO("MODULO"), OPERACION("OPERACION");

	private String value;

	ResourceType(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static ResourceType fromValue(String text) {
		for (ResourceType b : ResourceType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
