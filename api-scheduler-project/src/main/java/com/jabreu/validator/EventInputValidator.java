package com.jabreu.validator;

import com.jabreu.utils.ConstantesUtil;

public class EventInputValidator {

	public static boolean thisIsNormalEvent(String eventNameInput) {
		return eventNameInput != null && !eventNameInput.isEmpty()
				&& eventNameInput.matches(ConstantesUtil.NORMAL_EVENT_REGEX);
	}

	public static boolean thisIsLightningEvent(String eventNameInput) {
		return eventNameInput != null && !eventNameInput.isEmpty()
				&& eventNameInput.matches(ConstantesUtil.LIGHTNING_EVENT_REGEX);
	}

	public static boolean thisEventIsValid(String eventNameInput) {
		return thisIsNormalEvent(eventNameInput) || thisIsLightningEvent(eventNameInput);
	}

}
