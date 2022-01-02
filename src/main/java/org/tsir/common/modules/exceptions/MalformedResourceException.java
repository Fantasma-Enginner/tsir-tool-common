package org.tsir.common.modules.exceptions;

public class MalformedResourceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4177191866031893758L;

	private String resource;

	public MalformedResourceException(String resource) {
		super("El codigo de autoridad del recurso no es correcto F" + resource);
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}
}
