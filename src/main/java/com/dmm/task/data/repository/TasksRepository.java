package com.dmm.task.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmm.task.data.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Long>{

	List<Tasks> findAllByName(String name);

}
