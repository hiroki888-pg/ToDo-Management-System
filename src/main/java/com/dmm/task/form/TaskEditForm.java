package com.dmm.task.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TaskEditForm {
	@Size(min = 1, max = 200)
	private String title;
	@Size(min = 1, max = 200)
	private String text;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	@NotNull
	private boolean done;
	
	public boolean isActive() {
        return done;
    }

    public void setActive(boolean active) {
        done = active;
    }
	

}
