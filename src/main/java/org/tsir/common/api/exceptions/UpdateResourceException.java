package org.tsir.common.api.exceptions;

public class UpdateResourceException extends ServerProcessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3295917512257772944L;

	public UpdateResourceException(String resource, Exception e) {
		super("Falla en la actualización de datos para " + resource, e);
	}

}
