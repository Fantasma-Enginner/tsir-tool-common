package org.tsir.common.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-25T16:18:59.067Z[GMT]")
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6343104973253527520L;
	private final String resourceName;
	private final String fieldName;
	private final String fieldValue;

	public ConflictException(String resourceName, String fieldName, String fieldValue) {
		this(resourceName, fieldName, fieldValue,
				String.format("%s con %s  '%s' ya se encuentra registrado.", resourceName, fieldName, fieldValue));
	}

	public ConflictException(String resourceName, String fieldName, String fieldValue, String message) {
		super(message);
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

}
