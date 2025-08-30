package com.todo.todo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.todo.todo.dto.request.CreateTaskRequest;
import com.todo.todo.dto.request.UpdateTaskRequest;
import com.todo.todo.dto.response.TaskResponse;
import com.todo.todo.model.Task;
import com.todo.todo.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")     // frontend url
public class TaskController {
    
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks - Get all tasks with optional filtering by completion status
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(required = false) Boolean completed) {
        List<Task> tasks;
        if (completed != null) {
            tasks = taskService.getTasksByCompletionStatus(completed);
        } else {
            tasks = taskService.getAllTasks();
        }
        List<TaskResponse> taskResponses = tasks.stream()
            .map(TaskResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok(taskResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(TaskResponse.from(task));
    }
    
    // POST /api/tasks - Create new task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task createdTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponse.from(createdTask));
    }

    // PUT /api/tasks/{id} - Update entire task
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTas(
        @PathVariable Long id,
        @Valid @RequestBody UpdateTaskRequest request) {
            Task updatedTask = taskService.updateTask(id, request);
            return ResponseEntity.ok(TaskResponse.from(updatedTask));
    }
    
    // PATCH /api/tasks/{id}/title - Update only title
    @PatchMapping("/{id}/title")
    public ResponseEntity<TaskResponse> updateTitle(
        @PathVariable Long id,
        @RequestBody Map<String, String> request) {
            String title = request.get("title");
            Task updatedTask = taskService.updateTitle(id, title);
            return ResponseEntity.ok(TaskResponse.from(updatedTask));
    }
    
    // PATH /api/tasks/{id}/description - Update only description
    @PatchMapping("/{id}/description")
    public ResponseEntity<TaskResponse> updateDescription(
        @PathVariable Long id,
        @RequestBody Map<String, String> request) {
            String description = request.get("description");
            Task updatedTask = taskService.updateDescription(id, description);
            return ResponseEntity.ok(TaskResponse.from(updatedTask));
    }
    
    // PATCH /api/tasks/{id}/priority - Update only priority
    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskResponse> updatePriority(
        @PathVariable Long id,
        @RequestBody Map<String, Integer> request) {
            Integer priority = request.get("priority");
            Task updatedTask = taskService.updatePriority(id, priority);
            return ResponseEntity.ok(TaskResponse.from(updatedTask));
    }
    
    // PATCH /api/tasks/{id}/dueDate - Update only dueDate
    @PatchMapping("/{id}/dueDate")
    public ResponseEntity<TaskResponse> updateDueDate(
        @PathVariable Long id,
        @RequestBody Map<String, LocalDateTime> request) {
            LocalDateTime dueDate = request.get("dueDate");
            Task updatedTask = taskService.updateDueDate(id, dueDate);
            return ResponseEntity.ok(TaskResponse.from(updatedTask));
    }

    // PATCH /api/tasks/{id}/completed - update only completion status
    @PatchMapping("/{id}/completed")
    public ResponseEntity<TaskResponse> updateCompleted(
        @PathVariable Long id,
        @RequestBody Map<String, Boolean> request) {
            Boolean completed = request.get("completed");
            Task updatedTask = taskService.updateCompleted(id, completed);
            return ResponseEntity.ok(TaskResponse.from(updatedTask));
    }
    
    // DELETE /api/tasks/{id} - Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


}
