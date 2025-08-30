package com.todo.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.todo.dto.request.CreateTaskRequest;
import com.todo.todo.dto.request.UpdateTaskRequest;
import com.todo.todo.exception.TaskNotFoundException;
import com.todo.todo.model.Task;
import com.todo.todo.repository.TaskRepository;
import com.todo.todo.validation.TaskValidator;


import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskValidator taskValidator;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskValidator taskValidator) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
    }

    // GET TASKS BY COMPLETION STATUS
    @Transactional(readOnly = true)
    public List<Task> getTasksByCompletionStatus(boolean completed) {
        return taskRepository.findByCompletedOrderByPriorityDesc(completed);
    }

    // GET ALL TASKS
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByPriorityDesc();
    }

    // GET TASK BY ID
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        taskValidator.validateTaskId(id);
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    // CREATE NEW TASK
    public Task createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setCompleted(false);
        
        return taskRepository.save(task);
    }

    // UPDATE TASK
    public Task updateTask(Long id, UpdateTaskRequest request) {
        Task task = getTaskById(id); // this will throw an exception if not found

        // Only update fields that are provided (not null)
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getCompleted() != null) {
            task.setCompleted(request.getCompleted());
        }

        // JPA automatically calls @PreUpdate, so updatedAt is set automatically
        return taskRepository.save(task);
    }

    // UPDATE ONLY TITLE
    public Task updateTitle(Long id, String title) {
        Task task = getTaskById(id);
        task.setTitle(title);
        return taskRepository.save(task);
    }

    // UPDATE ONLY description
    public Task updateDescription(Long id, String description) {
        Task task = getTaskById(id);
        task.setDescription(description);
        return taskRepository.save(task);
    }

    // UPDATE ONLY priority
    public Task updatePriority(Long id, Integer priority) {
        Task task = getTaskById(id);
        task.setPriority(priority);
        return taskRepository.save(task);
    }

    // UPDATE ONLY dueDate
    public Task updateDueDate(Long id, LocalDateTime dueDate) {
        Task task = getTaskById(id);
        task.setDueDate(dueDate);
        return taskRepository.save(task);
    }

    // UPDATE ONLY STATUS
    public Task updateCompleted(Long id, boolean completed) {
        Task task = getTaskById(id);
        task.setCompleted(completed);
        return taskRepository.save(task);
    }

    // DELETE TASK
    public void deleteTask(Long id) {
        taskValidator.validateTaskId(id);

        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }

        taskRepository.deleteById(id);
    }

}
