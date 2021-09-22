package br.com.builders.domain.exception;

public class ClienteValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClienteValidationException(final String message) {
		super(message);
	}

}
