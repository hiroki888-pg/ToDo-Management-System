package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class MainCotroller {
	
	@Autowired
	private TasksRepository tasksRepository;
	
	public Map<LocalDate, List<Tasks>> getTasksGroupedByDay() {
        // タスクのリストを取得
        List<Tasks> tasks = tasksRepository.findAll();

        // dateフィールドを基に日付でグループ化
        return tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getDate().toLocalDate()));
    }
	
	public Map<LocalDate, List<Tasks>> getTasksGroupedByName(String name) {
		List<Tasks> tasks = tasksRepository.findAllByName(name);
		
		// dateフィールドを基に日付でグループ化
        return tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getDate().toLocalDate()));
	}
	
	@GetMapping("/main")
	String main(Model model, Model model_task, @AuthenticationPrincipal AccountUserDetails user, 
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		
		List<List<LocalDate>> monthList = new ArrayList<List<LocalDate>>();
		List<LocalDate> weekList = new ArrayList<LocalDate>();
		
		if (date == null) {
            date = LocalDate.now();
        }

		LocalDate preMonth = date.minusMonths(1);
		LocalDate neMonth = date.plusMonths(1);
		LocalDate firstDay = date.withDayOfMonth(1);
		
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
		String formattedDate = date.format(formatter);
		
		int length = date.lengthOfMonth();
		LocalDate lastDay = date.withDayOfMonth(length);
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
    	
    	
    	model.addAttribute("prev", preMonth);
    	model.addAttribute("next", neMonth);
    	
    	
    	
    	if(user.getName().equals("admin-name")) {
    		Map<LocalDate, List<Tasks>> tasksGroupedByDay = getTasksGroupedByDay();
    		model.addAttribute("tasks", tasksGroupedByDay);
    	} else {
    		Map<LocalDate, List<Tasks>> tasksGroupedByName = getTasksGroupedByName(user.getName());
    		model.addAttribute("tasks", tasksGroupedByName);
    	}
		
		return "main";
	}

}
