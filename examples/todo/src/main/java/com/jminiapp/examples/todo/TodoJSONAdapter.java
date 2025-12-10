package com.jminiapp.examples.todo;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON format adapter for TodoItem objects.
 * Extends the framework's JSONAdapter to provide JSON serialization/deserialization
 * for todo items using Gson.
 */
public class TodoJSONAdapter implements JSONAdapter<TodoItem> {

    /**
     * Returns the class type for deserialization
     *
     * @return TodoItem.class
     */
    @Override
    public Class<TodoItem> getstateClass() {
        return TodoItem.class;
    }
}
