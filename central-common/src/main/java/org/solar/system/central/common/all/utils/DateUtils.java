package org.solar.system.central.common.all.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtils {
	
	public static final String DEFAULT_DATE_PATTERN 			= "yyyy-MM-dd HH:mm:SS";
	public static final String DEFAULT_DATE_PATTERN_NO_TIME 	= "yyyy-MM-dd";
	public static final String COMPLETE_DATE_STR_PATTERN 		= "yyyyMMddHHmmssSSS";
	
	public String toStringFormat(LocalDateTime dateTime, String... params) {
	
		if (params == null || params.length == 0) {
			return toStringFormat(dateTime);
		}
		if (dateTime != null && StringUtils.isNotBlank(params[0])) {
			return dateTime.format(formatterDateTime(params[0]));
		}
		return StringUtils.EMPTY;
		
	}
	
	public String toStringFormat(LocalDateTime dateTime) {
		if (dateTime != null) {
			return dateTime.format(defaultFormatterDateTime());
		}
		return StringUtils.EMPTY;
	}

	public String toStringFormat(LocalDate date) {

		if (date != null) {
			return date.format(defaultFormatterDate());
		}
		return StringUtils.EMPTY;

	}
	
	public DateTimeFormatter defaultFormatterDateTime() {
		return formatterDateTime(null);
	}

	public DateTimeFormatter defaultFormatterDate() {
		return formatterDate(null);
	}
	
	public static DateTimeFormatter formatterDateTime(String valueOrDefaultFormat) {
		if(StringUtils.isBlank(valueOrDefaultFormat)) {
			return DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
		}
		return DateTimeFormatter.ofPattern(valueOrDefaultFormat);
	}

	public static DateTimeFormatter formatterDate(String valueOrDefaultFormat) {
		if(StringUtils.isBlank(valueOrDefaultFormat)) {
			return DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN_NO_TIME);
		}
		return DateTimeFormatter.ofPattern(valueOrDefaultFormat);
	}

	public static LocalDate getCurrentDate() {
		return LocalDate.now();
	}

	public static String getCurrentMonth() {
		int month = LocalDate.now().getMonth().getValue();
		return month < 10 ? "0"+month : String.valueOf(month);
	}

	public static String getCurrentYear() {
		return String.valueOf(LocalDate.now().getYear());
	}

	/**
	 * Vérifie si une date est comprise entre deux autres dates (bornes incluses).
	 *
	 * @param date La date à vérifier.
	 * @param startDate La date de début de l'intervalle.
	 * @param endDate La date de fin de l'intervalle.
	 * @return true si la date est comprise, false sinon.
	 */
	public static boolean isBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
		return !date.isBefore(startDate) && !date.isAfter(endDate);
	}

	/**
	 * Extrait les deux derniers chiffres de l'année d'une date sous forme de chaîne.
	 *
	 * @param date La date dont on veut extraire les deux derniers chiffres de l'année.
	 * @return Les deux derniers chiffres de l'année sous forme de chaîne.
	 */
	public static int getTwoLastDigitsYearAsString(LocalDate date) {
		return date.getYear() % 100;
	}

}
