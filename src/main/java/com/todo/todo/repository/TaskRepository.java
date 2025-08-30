package com.todo.todo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompletedOrderByPriorityDesc(boolean completed);
    List<Task> findByPriorityGreaterThanEqual(Integer priority);
    List<Task> findByDueDateBefore(LocalDateTime date);
    List<Task> findAllByOrderByPriorityDesc();
}
