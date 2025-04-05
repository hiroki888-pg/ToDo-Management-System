package com.dmm.task.form;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TaskForm {
	
	@Size(min = 1, max = 200)
	private String title;
	@Size(min = 1, max = 200)
	private String text;
	@Size(min = 10, max = 10)
	private String date;
	private boolean done;

}
