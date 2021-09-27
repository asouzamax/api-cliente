package br.com.builders.api.cliente.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Object[] valores;

	/**
	 * Cria o objeto.
	 */
	public BusinessException() {
		super();
	}

	/**
	 * Cria o objeto e atribui a mensagem da exce��o.
	 * 
	 * @param mensagem Mensagem da exce��o
	 */
	public BusinessException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Cria o objeto e atribui a mensagem e a causa da exce��o.
	 * 
	 * @param mensagem Mensagem da exce��o
	 * @param causa    Causa da exce��o
	 */
	public BusinessException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	/**
	 * Cria o objeto e atribui a causa da exce��o.
	 * 
	 * @param causa Causa da exce��o
	 */
	public BusinessException(Throwable causa) {
		super(causa);
	}

	/**
	 * Cria o objeto e atribui a referencia ao arquivo de propriedades.
	 * 
	 * @param mensagem Chave no arquivo de propriedades
	 * @param _valores Valores relacionados a chave
	 */
	public BusinessException(String mensagem, Object[] _valores) {
		super(mensagem);
		this.valores = _valores;
	}

}
