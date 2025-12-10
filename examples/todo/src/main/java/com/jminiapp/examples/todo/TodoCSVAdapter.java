package com.jminiapp.examples.todo;

import com.jminiapp.core.adapters.CSVAdapter;

/**
 * CSV format adapter for TodoItem objects.
 * Implements the framework's CSVAdapter to provide CSV serialization/deserialization
 * for todo items.
 */
public class TodoCSVAdapter implements CSVAdapter<TodoItem> {

    /**
     * Returns the CSV header row
     *
     * @return array of column names
     */
    @Override
    public String[] getHeader() {
        return new String[]{"id", "title", "description", "completed", "priority", "createdAt"};
    }

    /**
     * Converts a TodoItem to CSV row format
     *
     * @param item the todo item to convert
     * @return array of field values
     */
    @Override
    public String[] toCSV(TodoItem item) {
        return new String[]{
            item.getId(),
            item.getTitle(),
            item.getDescription(),
            String.valueOf(item.isCompleted()),
            item.getPriority().toString(),
            item.getCreatedAt()
        };
    }

    /**
     * Converts a CSV row to a TodoItem object
     *
     * @param fields the array of field values
     * @return TodoItem instance
     */
    @Override
    public TodoItem fromCSV(String[] fields) {
        if (fields.length != 6) {
            throw new IllegalArgumentException(
                "Invalid CSV row: expected 6 fields, got " + fields.length
            );
        }

        TodoItem item = new TodoItem();
        item.setId(fields[0]);
        item.setTitle(fields[1]);
        item.setDescription(fields[2]);
        item.setCompleted(Boolean.parseBoolean(fields[3]));
        item.setPriority(TodoItem.Priority.valueOf(fields[4]));
        item.setCreatedAt(fields[5]);

        return item;
    }
}
