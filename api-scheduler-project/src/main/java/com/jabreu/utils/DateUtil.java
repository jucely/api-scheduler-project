package com.jabreu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	private static SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mma", Locale.US);
	
	public static Date setHourWithoutMinutes(Date date, Integer hour) {
		date = DateUtils.setHours(date, hour);
		return DateUtils.truncate(date, Calendar.HOUR);
	}
	
	public static String getHourFormat(Date date) {
		return hourFormat.format(date);
	}
	

}
