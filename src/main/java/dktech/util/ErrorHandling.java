package dktech.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ErrorHandling {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	public LocalDate localDate(String date) {
		if (!date.isBlank()) {
			if (date.length() != 8 && date.length() != 1) {
				throw new IllegalArgumentException("Invalid date format: " + date);
			} else if (date.length() == 1) {
				return LocalDate.now();
			} else {
				return LocalDate.parse(date, formatter);
			}
		} else {
			return LocalDate.now();
		}
	}
}
