package com.todo.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String title;

   @Column(nullable = false)
   private String description;

   @Column(nullable = false)
   private boolean completed = false;

   @Column(nullable = false)
   private Integer priority;

   @Column(name = "due_date")
   private LocalDateTime dueDate;

   @Column(name = "created_at", nullable = false, updatable = false)
   private LocalDateTime createdAt = LocalDateTime.now();

   @Column(name = "updated_at", nullable = false)
   private LocalDateTime updatedAt = LocalDateTime.now();

   // JPA automatically calls this before saving new entity
   @PrePersist
   protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
   }

   // JPA automatically calls this before updating existing entity
   @PreUpdate
   protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
   }

   // Business logic methods / priority labels
   public boolean isHighPriority() {
        return priority != null && priority >= 4;
   }

   public boolean isLowPriority() {
    return priority != null && priority <= 2;
   }

   public String getPriorityLabel() {
        if (priority == null) return "Priority is not set";
        return switch (priority) {
            case 1 -> "Very low";
            case 2 -> "Low";
            case 3 -> "Medium";
            case 4 -> "High";
            case 5 -> "Very High";
            default -> "Priority is not set";
        };
   }
   
}
