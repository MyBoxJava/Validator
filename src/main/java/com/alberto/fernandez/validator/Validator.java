package com.alberto.fernandez.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	
	/**
	 * PATTERN: Son expresiones regulares para la validacion de EMAIL y DNI
	 */
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_DNI = "(\\d{8})([-]?)([A-Z]{1})";
	
	
	private static final String NIF_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
	
	
	/**
	 * comprueba si el email es valido con la expresion regular <code>this.PATTERN_EMAIL</code>
	 * @param email <String> email a validar
	 * @return true si el email valido, false en caso contrario
	 */
	public static boolean isEmail(String email){
		boolean resul = false;
		
		if (email == null){
			resul = false;
		}
		else{
	        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
	 
	        Matcher matcher = pattern.matcher(email);
	        resul = matcher.matches();
		}
		
		return resul;
	}
	
	

	/**
	 * Metodo que valida si es correcto el DNI Documento Nacional de Identidad España
	 * 
	 * @param nif <code>String</code>
	 * @return <code>boolean</code> true si es correcto, false en caso contrario
	 */
	public static boolean isDniValido(String nif) {

		boolean resultado = false;

		try {
			String nif1 = nif.toUpperCase();
			if (nif1.matches("[0-9]{8}[" + NIF_LETTERS + "]")) {
				int dni = Integer.parseInt(nif1.substring(0, 8));
				char letraCalculada = NIF_LETTERS.charAt(dni % 23);
				if (letraCalculada == nif1.charAt(8)) {
					resultado = true;
				}
			}
		} catch (Exception e) {
			// Si ha habido algún error es porque hay algún parseo que tira bien.
			resultado = false;
		}

		return resultado;
	}
	
	
	/**
	 * Metodo que calcula los años pasados a partir de una fecha
	 * 
	 * @param fecha
	 *            <code>Date</code> La fecha a partir de la cual se calculan los
	 *            años
	 * @return <code>int</code> con los años que han pasado desde la fecha dada
	 *         (simpre va a ser >= 0)
	 * 
	 * @throws NullPointerException
	 *             en caso de que el parametro recibido sea null
	 * @throws ValidatorException
	 *             en caso de que la fecha recibida por parametro sea posterior
	 *             a la actual
	 */
	public static int calcularEdad(java.util.Date fecha) throws NullPointerException, ValidatorException {
		if (fecha == null) {
			throw new NullPointerException();
		}
		if (System.currentTimeMillis() < fecha.getTime()) {
			throw new ValidatorException(ValidatorException.MSG_FECHA_FUTURA);
		}

		int diferencia = -1;

		Calendar calendarA = Calendar.getInstance(), calendarP = Calendar.getInstance();

		calendarA.setTimeInMillis(System.currentTimeMillis());
		calendarP.setTimeInMillis(fecha.getTime());

		diferencia = calendarA.get(Calendar.YEAR) - calendarP.get(Calendar.YEAR);

		return diferencia;
	}
}