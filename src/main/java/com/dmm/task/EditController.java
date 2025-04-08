package com.dmm.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TaskEditForm;
import com.dmm.task.service.AccountUserDetails;

@RequestMapping("")
@Controller
public class EditController {
	
	@Autowired
	private TasksRepository tasksRepositoryOfget;
	private TasksRepository tasksRepositoryOfset;
	@GetMapping("/main/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Optional<Tasks> opTask = tasksRepositoryOfget.findById(id);
		if (opTask.isEmpty()) {
	        // Taskが存在しない場合、適切なエラーメッセージや処理を返す
	        model.addAttribute("error", "Task not found for id: " + id);
	        return "main";
	    }
		Tasks task = opTask.get();
		model.addAttribute("task", task);
		
		
		return "edit";
	}
	
	@PostMapping("/main/edit/{id}")
	public String edit(@PathVariable("id") Long id, @Validated TaskEditForm taskEditForm, @AuthenticationPrincipal AccountUserDetails user, Model model) {
		
		Tasks task = new Tasks();
		String title = taskEditForm.getTitle();
		if(title == null) {
			throw new IllegalArgumentException();
		}
		
		String name = user.getName();
		String text = taskEditForm.getText();
		if(text == null) {
			throw new IllegalArgumentException();
		}
		
		LocalDate date = taskEditForm.getDate();
		LocalDateTime localDateTime = date.atStartOfDay();
		if(localDateTime == null) {
			throw new IllegalArgumentException();
		}
		
		boolean done = taskEditForm.isActive();
		
		task.setId(id);
		task.setTitle(title);
		task.setName(name);
		task.setText(text);
		task.setDate(localDateTime);
		task.setDone(done);
		
		tasksRepositoryOfset.save(task);
		return "redirect:/main";
	}
	
	

}
