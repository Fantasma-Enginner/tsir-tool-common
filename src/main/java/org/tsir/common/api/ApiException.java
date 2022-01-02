package org.tsir.common.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-25T16:18:59.067Z[GMT]")
public class ApiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5453028090532983926L;
	private int code;

	public ApiException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
