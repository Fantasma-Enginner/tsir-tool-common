package org.tsir.common.services.resources.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración de estados generales de activación: * `INACTIVO` - Objeto
 * inactivo. * `ACTIVO` - Objeto activo. * `SUSPENDIDO` - Objeto inhabilitado
 * temporalmente.
 */
public enum StateActive {
	INACTIVO("INACTIVO"), ACTIVO("ACTIVO"), SUSPENDIDO("SUSPENDIDO");

	private String value;

	StateActive(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static StateActive fromValue(String text) {
		for (StateActive b : StateActive.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
