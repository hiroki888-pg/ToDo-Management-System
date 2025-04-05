package com.dmm.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	
	@PostMapping("/main")
	public String create(@Validated TaskForm taskForm, BindingResult bindingResult, @AuthenticationPrincipal AccountUserDetails user) {
		if (bindingResult.hasErrors()) {
			
			return "create";
		}
		Tasks task = new Tasks();
		String title = taskForm.getTitle();
		String text = taskForm.getText();
		String date = taskForm.getDate();
		boolean done = false;
		
		task.setTitle(title);
		task.setText(text);
		task.setDate(date);
		task.setDone(done);
		
		tasksRepository.save(task);
		
		return "redirect:/main";
	}

}
