package org.tsir.common.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerProcessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3295917512257772944L;

	private final String description;

	public ServerProcessException(String description, Exception e) {
		super(e);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
