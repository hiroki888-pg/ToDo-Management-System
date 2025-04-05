package com.dmm.task.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TaskForm {
	
	@Size(min = 1, max = 200)
	private String title;
	@Size(min = 1, max = 200)
	private String text;
	@NotNull(message = "タスクの日付は必須です")
	private LocalDate date;

}
