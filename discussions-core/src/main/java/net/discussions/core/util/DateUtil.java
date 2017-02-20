package net.discussions.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateUtil {

	public static Integer periodInYears(LocalDate inicialDate, LocalDate finalDate) {
		Period period = Period.between(inicialDate, finalDate);
		return (period.getMonths()+1) == 12 ? period.getYears()+1 : period.getYears();
	}
	
	public static Integer periodInMonths(LocalDate inicialDate, LocalDate finalDate) {
		Period period = Period.between(inicialDate, finalDate);
		return (period.getMonths()+1) == 12 ? 0 : period.getMonths();
	}
	
	public static LocalDateTime stringToLocalDateTime(String dateString) {
		List<String> ds = Arrays.asList(dateString.split(","));
		
		for (int i = 3; i < ds.size(); i++) {
			ds.set(i, ds.get(i).length() == 1 ? "0"+ds.get(i) : ds.get(i));
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
		LocalDate date = LocalDate.of(Integer.valueOf(ds.get(0)), Integer.valueOf(ds.get(1)), Integer.valueOf(ds.get(2)));
		String timeString =  ds.get(3) + ":" + ds.get(4);
		timeString = ds.size() == 6 ?  timeString + ":" + ds.get(5) : timeString + ":00";
		LocalTime time = LocalTime.parse(timeString ,formatter);
        
        return date.atTime(time);
	}
	
}
