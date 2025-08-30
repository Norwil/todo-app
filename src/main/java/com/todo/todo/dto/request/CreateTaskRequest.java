package com.todo.todo.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;


public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "title must be 3-255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be 10-1000 characters")
    private String description;

    @NotNull(message = "Priority is required")
    @Min(value = 1, message = "Priority must be 1-5")
    @Max(value = 5, message = "Priority must be 1-5")
    private Integer priority;

    private LocalDateTime dueDate; // Optional

    public CreateTaskRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    
}
