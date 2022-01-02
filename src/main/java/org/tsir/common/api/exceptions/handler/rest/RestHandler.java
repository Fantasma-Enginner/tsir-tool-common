package org.tsir.common.api.exceptions.handler.rest;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.tsir.common.api.exceptions.ConflictException;
import org.tsir.common.api.exceptions.DataTypeCriteriaException;
import org.tsir.common.api.exceptions.InconsistentDataException;
import org.tsir.common.api.exceptions.NotFoundException;
import org.tsir.common.api.exceptions.NotSupportCriteriaException;
import org.tsir.common.api.exceptions.ServerProcessException;
import org.tsir.common.api.exceptions.handler.BaseExceptionHandler;

@ControllerAdvice
public final class RestHandler extends BaseExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		HttpStatus throwStatus = HttpStatus.BAD_REQUEST;
		String message = String.format(FAIL_CONVERSION_MSSG_TEMPLATE, ex.getPropertyName(), ex.getValue(),
				ex.getLocalizedMessage());
		headers = checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(throwStatus, message), headers, throwStatus, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder errors = new StringBuilder();
		errors.append("Error en campos de solicitud: ");
		ex.getBindingResult().getFieldErrors()
				.forEach(f -> errors.append(f.getField()).append(": ").append(f.getDefaultMessage()));
		ex.getBindingResult().getGlobalErrors()
				.forEach(f -> errors.append(f.getObjectName()).append(": ").append(f.getDefaultMessage()));
		headers = checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(status, errors.toString()), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Error en la solicitud. Valor incorrecto '" + ex.getValue().toString() + "', se espera tipo "
				+ ex.getRequiredType().getSimpleName();
		headers = checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(status, error), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String message = "Error en campos de solicitud: ";
		headers = checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(status, message), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" tipo de contenido solicitado no soportado. Tipos soportados = ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
		headers = checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(status, builder.toString()), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "No es posible generar los tipos de contenidos solicitados: " + request.getHeader("Accept");
		headers = checkMediaType(request, headers);
		return handleExceptionInternal(ex, buildApiError(status, message), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append("Metodo ");
		builder.append(ex.getMethod());
		builder.append(" no soportado para la solicitud. Los metodos soportados son: ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
		headers = checkMediaType(request, headers);
		return new ResponseEntity<>(buildApiError(status, builder.toString()), headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(buildApiError(status, "No ha sido posible convertir el mensaje"), headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(
				buildApiError(status, "Operación realizada, pero no ha sido posible convertir respuesta"), headers,
				status);
	}

	@ExceptionHandler(value = NotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFound(NotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		HttpHeaders headers = checkMediaType(request, null);
		return handleExceptionInternal(ex, buildApiError(status, ex.getMessage()), headers, status, request);
	}

	@ExceptionHandler({ ConflictException.class, InconsistentDataException.class })
	protected ResponseEntity<Object> handleResourceConflict(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		HttpHeaders headers = checkMediaType(request, null);
		return handleExceptionInternal(ex, buildApiError(status, ex.getMessage()), headers, status, request);
	}

	@ExceptionHandler({ InvalidDataAccessResourceUsageException.class, ServerProcessException.class })
	protected ResponseEntity<Object> handleServerError(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String message = "Ha ocurrido un error en el proceso";
		HttpHeaders headers = checkMediaType(request, null);
		log.warn("Error de acceso a datos", ex);
		return handleExceptionInternal(ex, buildApiError(status, message), headers, status, request);
	}

	@ExceptionHandler({ NotSupportCriteriaException.class, DataTypeCriteriaException.class })
	protected ResponseEntity<Object> requestDataError(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		HttpHeaders headers = checkMediaType(request, null);
		log.warn("Error de acceso a datos", ex);
		return handleExceptionInternal(ex, buildApiError(status, ex.getMessage()), headers, status, request);
	}

}
