import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.alberto.fernandez.validator.Validator;
import com.alberto.fernandez.validator.ValidatorException;


public class ValidatorTest {

	@Test
	public void testIsEmail() {

		assertTrue(Validator.isEmail("auraga@ipartek.com"));
		assertTrue("Deberia permitir subdominios al final", Validator.isEmail("auraga@ipartek.com.eu"));
		assertTrue("Minimo dos caracteres en dominio", Validator.isEmail("a@a.aa"));

		assertFalse(Validator.isEmail(null));
		assertFalse(Validator.isEmail(""));
		assertFalse(Validator.isEmail("@"));
		assertFalse(Validator.isEmail("a@a.a"));

		assertFalse("falta primer parte", Validator.isEmail("@ipartek.com"));
		assertFalse("falta segunda parte", Validator.isEmail("auraga@.com"));
		assertFalse("falta punto al final", Validator.isEmail("auraga@gmailcom"));
		// TODO algun dia si hay tiempo investigar
		// assertFalse("No puede haber un tercer
		// subdominio",Validator.isEmail("auraga@ipartek.com.eu.otro"));

		String[] validEmails = new String[] { "test@example.com", "test-101@example.com", "test.101@yahoo.com",
				"test101@example.com", "test_101@example.com", "test-101@test.net", "test.100@example.com.au",
				"test@e.com", "test@1.com", "test@example.com.com", "test+101@example.com", "101@example.com",
				"test-101@example-test.com" };

		String[] invalidEmails = new String[] { "example", "example@.com.com", "exampel101@test.a", "exampel101@.com",
				".example@test.com", "example**()@test.com", "example@%*.com", "example..101@test.com",
				"example.@test.com", "test@example_101.com", "example@test@test.com", "example@test.com.a5" };

		for (String temp : validEmails) {

			assertTrue("email [" + temp + "] deberia ser valido", Validator.isEmail(temp));

		}

		for (String temp : invalidEmails) {

			assertEquals("email [" + temp + "] NO deberia ser valido", Validator.isEmail(temp), false);

		}

	}

	@Test
	public void testIsDniValido() {
		
	
		String [] validDNIs = new String [] {"45819968N", "95717063X", "36459170F",
     		"68129543R","82449488P","02556385G","74369217M",
     		"54970460T","45551166B","30113589B","17518438M"};
     
		String [] invalidDNIs = new String [] { null, "4819968N", "9571706xX", "",
     		"miDNI","123456789A","025565G","1174369217M",
     		"9numeros1letra","etc","...","demás cosas"};
		
		for ( String dni : validDNIs ){
			assertTrue("[" + dni + "] deberia ser valido", Validator.isDniValido(dni));			
		}
		
		for ( String dni : invalidDNIs ){
			assertFalse("[" + dni + "] NO deberia ser valido", Validator.isDniValido(dni));			
		}
	
	}
	
	
	@Test
	public void testCalculoEdad() {
		int edad = 7;
		long timeInMillis = System.currentTimeMillis();
		
		Calendar calendar = Calendar.getInstance();
		Date fecha;
		//Prueba con una diferencia de 7 años		
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - edad);
		
		fecha = calendar.getTime();
		
		try {
			assertEquals(edad, Validator.calcularEdad(fecha));
		} catch (Exception e) {
			fail("No deberia haber fallado");
		} 
		
		//Prueba con la misma fecha
		edad = 0;
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - edad);
		
		fecha = calendar.getTime();
		
		try {
			assertEquals("No son la misma fecha", edad, Validator.calcularEdad(fecha));
		} catch (Exception e) {
			fail("No deberia fallar");
		}	
		
		
		//Prueba con una fecha a null
		
		try {
			assertEquals(edad, Validator.calcularEdad(null));
			fail("Tenia que haber saltado NullPointerException");
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (ValidatorException e) {
			fail("No deberia saltar esta excepcion");
		}
		
		//Prueba con una fecha posterior
		edad = -15;
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - edad);
		
		fecha = calendar.getTime();
		
		try {
			assertEquals(edad, Validator.calcularEdad(fecha));
			fail("Tenia que haber saltado ValidatorException");
		} catch (NullPointerException e) {
			fail("No deberia saltar esta excepcion");
		} catch (ValidatorException e) {
			assertEquals(ValidatorException.MSG_FECHA_FUTURA, e.getMessage());
		}
	}

}
