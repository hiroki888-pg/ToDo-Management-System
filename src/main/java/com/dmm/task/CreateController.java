package com.dmm.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String create(@Validated TaskForm taskForm, @AuthenticationPrincipal AccountUserDetails user, Model model) {
		
		Tasks task = new Tasks();
		String title = taskForm.getTitle();
		String name = user.getName();
		String text = taskForm.getText();
		LocalDate date = taskForm.getDate();
		LocalDateTime localDateTime = date.atStartOfDay();
		boolean done = false;
		
		task.setTitle(title);
		task.setName(name);
		task.setText(text);
		task.setDate(localDateTime);
		task.setDone(done);
		
		tasksRepository.save(task);
		
		return "redirect:/main";
	}

}
