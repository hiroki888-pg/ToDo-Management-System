package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainCotroller {
	@GetMapping("/main")
	String main(Model model) {
		
		
		List<List<LocalDate>> monthList = new ArrayList<List<LocalDate>>();
		List<LocalDate> weekList = new ArrayList<LocalDate>();
		
		LocalDate now = LocalDate.now();
		LocalDate firstDay = now.withDayOfMonth(1);
//		DayOfWeek firstDate = firstDay.getDayOfWeek();
//		int DayValue = firstDate.getValue();
//		int zenDayValue = -DayValue;
		
		LocalDate zengetsuDay = firstDay;
		DayOfWeek zengetsuDate = zengetsuDay.getDayOfWeek();
		int zengetsuValue = zengetsuDate.getValue();
		
		int j;
		if(zengetsuValue == 1) {
			j = 1;
			while(j > 0) {
				zengetsuDay = firstDay.minusDays(j);
				weekList.add(zengetsuDay);
				j--;
			}
		} else if(zengetsuValue == 2) {
			j = 2;
			while(j > 0) {
				zengetsuDay = firstDay.minusDays(j);
				weekList.add(zengetsuDay);
				j--;
			}
		} else if(zengetsuValue == 3) {
			j = 3;
			while(j > 0) {
				zengetsuDay = firstDay.minusDays(j);
				weekList.add(zengetsuDay);
				j--;
			}
		} else if(zengetsuValue == 4) {
			j = 4;
			while(j > 0) {
				zengetsuDay = firstDay.minusDays(j);
				weekList.add(zengetsuDay);
				j--;
			}
		} else if(zengetsuValue == 5) {
			j = 5;
			while(j > 0) {
				zengetsuDay = firstDay.minusDays(j);
				weekList.add(zengetsuDay);
				j--;
			}
		} else if(zengetsuValue == 6) {
			j = 6;
			while(j > 0) {
				zengetsuDay = firstDay.minusDays(j);
				weekList.add(zengetsuDay);
				j--;
			}
		} else if(zengetsuValue == 7) {
			
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");
		String formattedDate = now.format(formatter);
		
		int length = now.lengthOfMonth();
		LocalDate lastDay = now.withDayOfMonth(length);
		DayOfWeek lastDate = lastDay.getDayOfWeek();
		
		LocalDate nextDay = firstDay;
		DayOfWeek nextDate = nextDay.getDayOfWeek();
		
		DayOfWeek lastDayOfWeek = lastDay.getDayOfWeek();
		int daysUntilSaturday = DayOfWeek.SATURDAY.getValue() - lastDayOfWeek.getValue();
		
		if (daysUntilSaturday < 0) {
            daysUntilSaturday += 7;
        }
		
		LocalDate lastSaturday = lastDay.plusDays(daysUntilSaturday);
		
		long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(firstDay, lastSaturday);
		
		if(lastDate.getValue() != 7) {
			daysBetween += 7;
		}
		
		for(int i = 0; i < daysBetween; i++) {
			nextDay = firstDay.plusDays(i);
			weekList.add(nextDay);
			nextDate = nextDay.getDayOfWeek();
			if (nextDate.getValue() == 6) {
				monthList.add(weekList);
				weekList = new ArrayList<>();
			}
			
		}
		
		
		
		List<List<LocalDate>> lastMonthOfMonthList = new ArrayList<List<LocalDate>>();
		List<LocalDate> lastMonthOfweekList = new ArrayList<LocalDate>();
		
		LocalDate lastMonthOfFirstDay = firstDay.minusMonths(1);
		int lastMonthOfLength = lastMonthOfFirstDay.lengthOfMonth();
		LocalDate lastMonthOflastDay = lastMonthOfFirstDay.withDayOfMonth(lastMonthOfLength);
		
		LocalDate lastMonthOfnextDay = lastMonthOfFirstDay;
		DayOfWeek lastMonthOfNextDate = lastMonthOfnextDay.getDayOfWeek();
		
		for(j = 0; j< lastMonthOflastDay.getDayOfMonth(); j++) {
			lastMonthOfnextDay = lastMonthOfFirstDay.plusDays(j);
			lastMonthOfweekList.add(lastMonthOfnextDay);
			lastMonthOfNextDate = lastMonthOfnextDay.getDayOfWeek();
			if (lastMonthOfNextDate.getValue() == 6) {
				lastMonthOfMonthList.add(lastMonthOfweekList);
				lastMonthOfweekList = new ArrayList<>();
			}
		}
		
		model.addAttribute("month", formattedDate);

    	model.addAttribute("matrix", monthList);
    	
		
		return "main";
	}
	
	@GetMapping("/main/{prev}")
	String prev(@PathVariable String prev, Model model) {
		
		model.addAttribute("prev", prev);
		
		List<List<LocalDate>> lastMonthOfMonthList = new ArrayList<List<LocalDate>>();
		List<LocalDate> lastMonthOfweekList = new ArrayList<LocalDate>();
		
		LocalDate now = LocalDate.now();
		LocalDate firstDay = now.withDayOfMonth(1);
		LocalDate lastMonthOfFirstDay = firstDay.minusMonths(1);
		int lastMonthOfLength = lastMonthOfFirstDay.lengthOfMonth();
		LocalDate lastMonthOflastDay = lastMonthOfFirstDay.withDayOfMonth(lastMonthOfLength);
		
		int year = lastMonthOfFirstDay.getYear();
		int month = lastMonthOfFirstDay.getMonthValue();
		
		LocalDate lastMonthOfnextDay = lastMonthOfFirstDay;
		DayOfWeek lastMonthOfNextDate = lastMonthOfnextDay.getDayOfWeek();
		
		for(int j = 0; j< lastMonthOflastDay.getDayOfMonth(); j++) {
			lastMonthOfnextDay = lastMonthOfFirstDay.plusDays(j);
			lastMonthOfweekList.add(lastMonthOfnextDay);
			lastMonthOfNextDate = lastMonthOfnextDay.getDayOfWeek();
			if (lastMonthOfNextDate.getValue() == 6) {
				lastMonthOfMonthList.add(lastMonthOfweekList);
				lastMonthOfweekList = new ArrayList<>();
			}
		}
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		
		model.addAttribute("prev", lastMonthOfFirstDay);
		
		model.addAttribute("weekList", lastMonthOfweekList);
    	model.addAttribute("monthList", lastMonthOfMonthList);
		
		return "lastOfMonth";
	}

}
