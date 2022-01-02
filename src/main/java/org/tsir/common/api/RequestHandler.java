package org.tsir.common.api;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestHandler {

	protected static final ResponseEntity<Void> RESPONSE_VOID_OK = ResponseEntity.ok().build();
	protected static final ResponseEntity<Void> RESPONSE_VOID_CREATED = ResponseEntity.created(null).build();

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HttpServletRequest request;

	public Optional<ObjectMapper> getObjectMapper() {
		return Optional.ofNullable(objectMapper);
	}

	public Optional<HttpServletRequest> getRequest() {
		return Optional.ofNullable(request);
	}

}
