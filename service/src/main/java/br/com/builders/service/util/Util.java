package br.com.builders.service.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


public class Util {
	
	private Util() {
	}
	
	public static void isValid(Long longValue) {
		if (longValue == null) {
			throw new IllegalArgumentException("Valor não pode ser nulo.");
		}
	}

	public static boolean isCollectionNullOrEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isNotNull(Object object) {
		return object != null;
	}
	
	public static LocalDateTime stringParaLocalDateTime(String dataHora) {

		if (dataHora == null) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		return LocalDateTime.parse(dataHora, formatter);
	}
	
	public static String localDateTimeParaString(LocalDateTime dataHora) {

		if (dataHora == null) {
			return null;
		}

		return dataHora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	public static LocalDate stringParaLocalDate(String data) {
		
		if (data == null) {
			return null;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return LocalDate.parse(data, formatter);
	}
	
	public static String localDateParaString(LocalDate data) {

		if (data == null) {
			return null;
		}

		return data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static BigDecimal stringParaBigDecimal(String dinheiro) {
		String valor = dinheiro.replace(",", ".").replace("%", "").replace("R", "").replace("$", "").trim();
		return new BigDecimal(valor);
	}
	
	public static Double stringParaDouble(String dinheiro) {
		String valor = dinheiro.replace(",", ".").replace("%", "").replace("R", "").replace("$", "").trim();
		return Double.valueOf(valor);
	}

}