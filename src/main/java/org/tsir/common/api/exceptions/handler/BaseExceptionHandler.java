package org.tsir.common.api.exceptions.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.tsir.common.api.ApiMessage;

public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

	private final List<String> supportMediaTypes = Collections
			.unmodifiableList(Arrays.asList(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE));

	protected static final String FAIL_CONVERSION_MSSG_TEMPLATE = "Conversion de campo '%s' con valor '%s' ha fallado. Causa: %s";

	protected Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

	protected ApiMessage buildApiError(HttpStatus status, String message) {
		return new ApiMessage().code(String.valueOf(status.value())).message(message);
	}

	protected HttpHeaders checkMediaType(WebRequest request, HttpHeaders headers) {
		if (headers == null) {
			headers = new HttpHeaders();
		}
		if (!isMediaTypeSupported(request)) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		return headers;
	}

	private boolean isMediaTypeSupported(WebRequest request) {
		String accept = request.getHeader("Accept");
		if (!Strings.isBlank(accept)) {
			for (String mediaType : supportMediaTypes) {
				if (accept.contains(mediaType)) {
					return true;
				}
			}
		}
		return false;
	}

}
