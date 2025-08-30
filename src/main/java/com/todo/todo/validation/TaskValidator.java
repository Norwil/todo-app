package com.todo.todo.validation;

import org.springframework.stereotype.Component;

import com.todo.todo.exception.ValidationException;

@Component
public class TaskValidator {

    // Extra business logic validation
    public void validateTaskId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("Invalid task ID");
        }
    }

    public void validateTaskForCompletion(String title, Integer priority) {
        if (priority >= 4 && title.toLowerCase().contains("test")) {
            throw new ValidationException("High priority tasks cannot contain 'test' in title");
        }
    }
    
}
