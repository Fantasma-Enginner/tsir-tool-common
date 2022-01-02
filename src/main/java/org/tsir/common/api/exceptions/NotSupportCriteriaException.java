package org.tsir.common.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-25T16:18:59.067Z[GMT]")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotSupportCriteriaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6343104973253527520L;
	private final String resourceName;
	private final String fieldName;

	public NotSupportCriteriaException(String resourceName, String criteria) {
		super(String.format("Criterio de busqueda '%s' no soportado para %s", criteria, resourceName));
		this.resourceName = resourceName;
		this.fieldName = criteria;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

}
