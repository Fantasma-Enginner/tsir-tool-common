package org.tsir.common.api.exceptions.handler.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.tsir.common.api.exceptions.handler.BaseExceptionHandler;

@ControllerAdvice
public final class SecuredRestHandler extends BaseExceptionHandler {

	@ExceptionHandler(value = AuthenticationException.class)
	protected ResponseEntity<Object> handleAuthenticationAccess(AuthenticationException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String message = "Falla de autenticación";
		HttpHeaders headers = new HttpHeaders();
		checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(status, message), headers, status, request);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	protected ResponseEntity<Object> handleAuthorizationAccess(AccessDeniedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		String message = "Operación no autorizada";
		HttpHeaders headers = new HttpHeaders();
		checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(status, message), headers, status, request);
	}

}
