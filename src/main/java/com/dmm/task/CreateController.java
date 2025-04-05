package com.dmm.task;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class CreateController {
	
	
	@Autowired
	private TasksRepository tasksRepository;
	@GetMapping("/main/create/{formatNowDate}")
	public String create() {
		
		return "create";
	}
	
	@PostMapping("/main/create")
	public String create(@Validated TaskForm taskForm, @AuthenticationPrincipal AccountUserDetails user) {
		
		Tasks task = new Tasks();
		String title = taskForm.getTitle();
		String name = user.getName();
		String text = taskForm.getText();
		LocalDateTime date = taskForm.getDate();
//		DateTimeFormatter formatter_bef = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//		LocalDate lDate = LocalDate.parse(date, formatter_bef);
//		DateTimeFormatter formatter_aft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		date = formatter_aft.format(lDate);
//		date = date + " 00:00:00";
		boolean done = false;
		
		task.setTitle(title);
		task.setName(name);
		task.setText(text);
		task.setDate(date);
		task.setDone(done);
		
		tasksRepository.save(task);
		
		return "redirect:/main";
	}

}
