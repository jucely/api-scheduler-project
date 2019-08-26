package com.jabreu.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jabreu.exception.InvalidEventInputException;
import com.jabreu.validator.EventInputValidator;

public class EventDurationParse {

	public static Integer toInteger(String eventInput) {
		String eventDuration = toString(eventInput);
		return Integer.valueOf(eventDuration);
	}

	public static String toString(String eventInput) {
		if (EventInputValidator.thisIsNormalEvent(eventInput)) {
			Matcher matcher = Pattern.compile(ConstantesUtil.NUMBER_REGEX).matcher(eventInput);
			return matcher.find() ? matcher.group() : null;
		} else if (EventInputValidator.thisIsLightningEvent(eventInput)) {
			return ConstantesUtil.LIGHTNING_EVENT_DURATION;
		}
		throw new InvalidEventInputException();
	}

}
