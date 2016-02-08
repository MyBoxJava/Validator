package com.alberto.fernandez.validator;

public class ValidatorException extends Exception {

	private static final long serialVersionUID = 1819017578265445047L;

	public static final String MSG_FECHA_FUTURA = "Incorrect data,it should be a data before today";
	public static final String MSG_FORMATO_DNI_NO_VALIDO = "Incorrect DNI format, it should have 8 numbers and one letter";

	public ValidatorException(String msg) {
		super(msg);
	}
}
