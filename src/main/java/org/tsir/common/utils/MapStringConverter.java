package org.tsir.common.utils;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.tsir.common.api.exceptions.ServerProcessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapStringConverter implements Converter<String, Map<String, String>> {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Map<String, String> convert(String source) {
		try {
			System.err.println(source);
			return mapper.readerForMapOf(String.class).readValue(source);
		} catch (JsonProcessingException e) {
			throw new ServerProcessException("Falla al convertir mapa de filtros", e);
		}
	}

}
