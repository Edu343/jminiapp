package com.jminiapp.examples.todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a todo item with unique ID, title, description, priority, and completion status.
 * This class serves as the state model for the Todo List application.
 */
public class TodoItem {

    /**
     * Priority levels for todo items
     */
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    private String id;
    private String title;
    private String description;
    private boolean completed;
    private Priority priority;
    private String createdAt;

    /**
     * Default constructor for deserialization
     */
    public TodoItem() {
        this.id = UUID.randomUUID().toString();
        this.completed = false;
        this.priority = Priority.MEDIUM;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Creates a new todo item with the specified title and description
     *
     * @param title the todo title
     * @param description the todo description
     */
    public TodoItem(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    /**
     * Creates a new todo item with title, description, and priority
     *
     * @param title the todo title
     * @param description the todo description
     * @param priority the priority level
     */
    public TodoItem(String title, String description, Priority priority) {
        this(title, description);
        this.priority = priority;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Toggles the completion status of this todo item
     */
    public void toggleComplete() {
        this.completed = !this.completed;
    }

    /**
     * Returns a formatted string representation of this todo item
     *
     * @return formatted string with todo details
     */
    @Override
    public String toString() {
        String status = completed ? "✓" : " ";
        String prioritySymbol = switch (priority) {
            case HIGH -> "!!!";
            case MEDIUM -> "!!";
            case LOW -> "!";
        };

        return String.format("[%s] %s %s - %s\n    ID: %s | Created: %s",
            status, prioritySymbol, title, description, id, createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return Objects.equals(id, todoItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
