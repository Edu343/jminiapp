package com.jminiapp.examples.todo;

import com.jminiapp.core.engine.JMiniAppRunner;

/**
 * Bootstrap class for the Todo List application.
 * Configures and launches the TodoApp using JMiniAppRunner.
 */
public class TodoAppRunner {

    /**
     * Main entry point for the Todo List application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(TodoApp.class)
            .withState(TodoItem.class)
            .withAdapters(
                new TodoJSONAdapter(),
                new TodoCSVAdapter()
            )
            .named("Todo")
            .run(args);
    }
}
