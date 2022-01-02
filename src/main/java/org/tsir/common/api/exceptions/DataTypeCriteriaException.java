package org.tsir.common.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-25T16:18:59.067Z[GMT]")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataTypeCriteriaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6343104973253527520L;
	private final String resourceName;
	private final String fieldName;
	private final String required;

	public DataTypeCriteriaException(String resourceName, String criteria, String required) {
		super(String.format("Tipo de dato erroneo en valor de criterio de busqueda '%s' para %s. Se require valor %s",
				criteria, resourceName, required));
		this.resourceName = resourceName;
		this.fieldName = criteria;
		this.required = required;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getRequired() {
		return required;
	}

}
