package com.jabreu.validator.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.jabreu.exception.InvalidEventInputException;
import com.jabreu.utils.EventDurationParse;
import com.jabreu.validator.EventInputValidator;

public class EventInputValidatorTest {

	@Test
	public void testNormalEventInputValidations() {

		assertTrue(EventInputValidator.thisIsNormalEvent("Overdoing it in Python 40005min"),
				"A normal event with long duration expected.");

		assertFalse(EventInputValidator.thisIsNormalEvent("Lua for the 3 Masses 30min"),
				"A normal event with numbers in title should be reject.");
		assertFalse(EventInputValidator.thisIsNormalEvent("Ruby Errors from Mismatched Gem Versions"),
				"A event without durantion is a lightning Event.");

	}

	@Test
	public void testNormalEventInputValidationWithMinDescriptionsVariations() {

		assertTrue(EventInputValidator.thisIsNormalEvent("Writing Fast Tests Against Enterprise Rails 60min"),
				"A normal event was expected with min.");
		assertTrue(EventInputValidator.thisIsNormalEvent("Writing Fast Tests Against Enterprise Rails 60Min"),
				"A normal event was expected with Min.");
		assertTrue(EventInputValidator.thisIsNormalEvent("Writing Fast Tests Against Enterprise Rails 60MIN"),
				"A normal event was expected with MIN.");
		assertTrue(EventInputValidator.thisIsNormalEvent("Writing Fast Tests Against Enterprise Rails 60minuto"),
				"A normal event was expected with minuto.");
		assertTrue(EventInputValidator.thisIsNormalEvent("Writing Fast Tests Against Enterprise Rails 60Minuto"),
				"A normal event was expected with Minuto.");
	}

	@Test
	public void testNormalEventInputValidationWithoutMinDescriptions() {
		assertFalse(EventInputValidator.thisIsNormalEvent("Writing Fast Tests Against Enterprise Rails 60"),
				"A normal event input without min would be rejected.");
	}

	@Test
	public void testLightningEventInputValidations() {
		assertTrue(EventInputValidator.thisIsLightningEvent("Writing Fast Tests Against Enterprise Rails"),
				"A lightning event was expected.");

		assertFalse(EventInputValidator.thisIsLightningEvent("Ruby Errors from Mismatched Gem Versions 40min"),
				"A event with durantion is a normal Event.");
		assertFalse(EventInputValidator.thisIsLightningEvent("Lua for the 3 Masses"),
				"A lightning event with numbers in title should be reject.");

	}

	@Test
	public void testEventInputValidations() {

		assertTrue(EventInputValidator.thisEventIsValid("Ruby on Rails: Why We Should Move On 60min"),
				"A normal event was expected with min.");
		assertTrue(EventInputValidator.thisEventIsValid("Writing Fast Tests Against Enterprise Rails"),
				"A lightning event was expected.");

		assertFalse(EventInputValidator.thisEventIsValid("Lua for the 3 Masses 30min"),
				"A normal event with numbers in title should be reject.");
		assertFalse(EventInputValidator.thisEventIsValid("Lua for the 3 Masses"),
				"A lightning event with numbers in title should be reject.");

	}

	@Test
	public void testEventInputDurationParse() {
		assertTrue(60 == EventDurationParse.toInteger("Writing Fast Tests Against Enterprise Rails 60min"),
				"Expected 60 minutos");
		assertTrue(600 == EventDurationParse.toInteger("Writing Fast Tests Against Enterprise Rails 600min"),
				"Expected 600 minutos");
		assertTrue(5 == EventDurationParse.toInteger("Writing Fast Tests Against Enterprise Rails"),
				"Expected 5 minutos");

		
		
		assertThrows(InvalidEventInputException.class,
				() -> EventDurationParse.toInteger("Writing Fast Tests Against Enterprise 5 Rails"),
				"Expected InvalidEventInputException when include invalid event");

	}

	
	
	@Test
	public void testEventInputEmpty() {
		assertFalse(EventInputValidator.thisIsNormalEvent(""), "A 'empty' input can't be a normal event.");
		assertFalse(EventInputValidator.thisEventIsValid(""), "A 'empty' input can't be a event.");
		assertFalse(EventInputValidator.thisIsLightningEvent(""), "A 'empty' input can't be a Lightning event.");

		assertThrows(InvalidEventInputException.class, () -> EventDurationParse.toInteger(""),
				"Expected InvalidEventInputException when include empty event");

	}

	@Test
	public void testEventInputNull() {

		assertFalse(EventInputValidator.thisEventIsValid(null), "A 'null' input can't be a event.");
		assertFalse(EventInputValidator.thisIsLightningEvent(null), "A 'null' input can't be a Lightning event.");
		assertFalse(EventInputValidator.thisIsNormalEvent(null), "A 'null' input can't be a normal event.");
		assertThrows(InvalidEventInputException.class, () -> EventDurationParse.toInteger(null),
				"Expected InvalidEventInputException when include null event");

	}

}
