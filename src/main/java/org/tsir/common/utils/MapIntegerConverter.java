package org.tsir.common.utils;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.tsir.common.api.exceptions.ServerProcessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapIntegerConverter implements Converter<String, Map<String, Integer>> {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Map<String, Integer> convert(String source) {
		try {
			return mapper.readerForMapOf(Integer.class).readValue(source);
		} catch (JsonProcessingException e) {
			throw new ServerProcessException("Falla al convertir mapa de filtros", e);
		}
	}

}
