package br.com.builders.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessHttpExcception extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final HttpStatus status;

	public BusinessHttpExcception(final HttpStatus status, final String message) {
		super(message);
		this.status = status;
	}

	public int cogetCode() {
		return status.value();
	}

}
