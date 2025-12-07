package com.jminiapp.examples.todo;

import com.jminiapp.core.api.ImportStrategies;
import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Todo List application that demonstrates CRUD operations, multiple format adapters,
 * and import strategies in the JMiniApp framework.
 */
public class TodoApp extends JMiniApp {

    private List<TodoItem> todos;
    private Scanner scanner;

    /**
     * Constructor required by the framework
     *
     * @param config the application configuration
     */
    public TodoApp(JMiniAppConfig config) {
        super(config);
    }

    /**
     * Initialize the application by loading existing todos or creating an empty list
     */
    @Override
    protected void initialize() {
        scanner = new Scanner(System.in);

        List<TodoItem> existingTodos = context.getData();
        if (existingTodos != null && !existingTodos.isEmpty()) {
            todos = new ArrayList<>(existingTodos);
            System.out.println("Loaded " + todos.size() + " existing todo(s)");
        } else {
            todos = new ArrayList<>();
            System.out.println("Starting with an empty todo list");
        }
    }

    /**
     * Main application loop presenting an interactive menu
     */
    @Override
    protected void run() {
        System.out.println("\n=== Welcome to Todo List Manager ===\n");

        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addTodo();
                case "2" -> listTodos("all");
                case "3" -> listTodos("pending");
                case "4" -> listTodos("completed");
                case "5" -> completeTodo();
                case "6" -> editTodo();
                case "7" -> deleteTodo();
                case "8" -> exportData();
                case "9" -> importData();
                case "0" -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("\nThank you for using Todo List Manager!");
    }

    /**
     * Save all todos to the context before shutdown
     */
    @Override
    protected void shutdown() {
        context.setData(todos);
        System.out.println("Todos saved successfully");
    }

    /**
     * Displays the main menu options
     */
    private void displayMenu() {
        System.out.println("\n--- Todo List Menu ---");
        System.out.println("1. Add new todo");
        System.out.println("2. List all todos");
        System.out.println("3. List pending todos");
        System.out.println("4. List completed todos");
        System.out.println("5. Mark todo as complete/incomplete");
        System.out.println("6. Edit todo");
        System.out.println("7. Delete todo");
        System.out.println("8. Export todos");
        System.out.println("9. Import todos");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");
    }

    /**
     * Adds a new todo item
     */
    private void addTodo() {
        System.out.println("\n--- Add New Todo ---");

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        }

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Priority (HIGH/MEDIUM/LOW) [MEDIUM]: ");
        String priorityInput = scanner.nextLine().trim().toUpperCase();

        TodoItem.Priority priority;
        try {
            priority = priorityInput.isEmpty() ? TodoItem.Priority.MEDIUM :
                       TodoItem.Priority.valueOf(priorityInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid priority. Using MEDIUM");
            priority = TodoItem.Priority.MEDIUM;
        }

        TodoItem newTodo = new TodoItem(title, description, priority);
        todos.add(newTodo);

        System.out.println("\nTodo added successfully!");
        System.out.println(newTodo);
    }

    /**
     * Lists todos filtered by status
     *
     * @param filter "all", "pending", or "completed"
     */
    private void listTodos(String filter) {
        System.out.println("\n--- " + filter.toUpperCase() + " Todos ---");

        List<TodoItem> filteredTodos = switch (filter) {
            case "pending" -> todos.stream()
                    .filter(t -> !t.isCompleted())
                    .collect(Collectors.toList());
            case "completed" -> todos.stream()
                    .filter(TodoItem::isCompleted)
                    .collect(Collectors.toList());
            default -> todos;
        };

        if (filteredTodos.isEmpty()) {
            System.out.println("No todos found");
            return;
        }

        for (int i = 0; i < filteredTodos.size(); i++) {
            System.out.println("\n" + (i + 1) + ". " + filteredTodos.get(i));
        }

        System.out.println("\nTotal: " + filteredTodos.size() + " todo(s)");
    }

    /**
     * Toggles the completion status of a todo
     */
    private void completeTodo() {
        if (todos.isEmpty()) {
            System.out.println("\nNo todos available");
            return;
        }

        listTodos("all");
        System.out.print("\nEnter todo number to toggle completion: ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;

            if (index < 0 || index >= todos.size()) {
                System.out.println("Invalid todo number");
                return;
            }

            TodoItem todo = todos.get(index);
            todo.toggleComplete();

            System.out.println("\nTodo updated:");
            System.out.println(todo);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number");
        }
    }

    /**
     * Edits an existing todo
     */
    private void editTodo() {
        if (todos.isEmpty()) {
            System.out.println("\nNo todos available");
            return;
        }

        listTodos("all");
        System.out.print("\nEnter todo number to edit: ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;

            if (index < 0 || index >= todos.size()) {
                System.out.println("Invalid todo number");
                return;
            }

            TodoItem todo = todos.get(index);
            System.out.println("\nEditing: " + todo.getTitle());

            System.out.print("New title [" + todo.getTitle() + "]: ");
            String title = scanner.nextLine().trim();
            if (!title.isEmpty()) {
                todo.setTitle(title);
            }

            System.out.print("New description [" + todo.getDescription() + "]: ");
            String description = scanner.nextLine().trim();
            if (!description.isEmpty()) {
                todo.setDescription(description);
            }

            System.out.print("New priority (HIGH/MEDIUM/LOW) [" + todo.getPriority() + "]: ");
            String priorityInput = scanner.nextLine().trim().toUpperCase();
            if (!priorityInput.isEmpty()) {
                try {
                    todo.setPriority(TodoItem.Priority.valueOf(priorityInput));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid priority. Keeping current value");
                }
            }

            System.out.println("\nTodo updated successfully!");
            System.out.println(todo);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number");
        }
    }

    /**
     * Deletes a todo item
     */
    private void deleteTodo() {
        if (todos.isEmpty()) {
            System.out.println("\nNo todos available");
            return;
        }

        listTodos("all");
        System.out.print("\nEnter todo number to delete: ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;

            if (index < 0 || index >= todos.size()) {
                System.out.println("Invalid todo number");
                return;
            }

            TodoItem removed = todos.remove(index);
            System.out.println("\nDeleted: " + removed.getTitle());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number");
        }
    }

    /**
     * Exports todos to a file in the specified format
     */
    private void exportData() {
        System.out.println("\n--- Export Todos ---");
        System.out.println("Supported formats: " + String.join(", ", context.getSupportedFormats()));
        System.out.print("Enter format (json/csv): ");

        String format = scanner.nextLine().trim().toLowerCase();

        if (!context.supportsFormat(format)) {
            System.out.println("Unsupported format: " + format);
            return;
        }

        System.out.print("Enter filename (without extension): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            filename = "todos";
        }

        try {
            context.exportData(format, filename + "." + format);
            System.out.println("Todos exported successfully to " + filename + "." + format);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    /**
     * Imports todos from a file with the specified strategy
     */
    private void importData() {
        System.out.println("\n--- Import Todos ---");
        System.out.println("Supported formats: " + String.join(", ", context.getSupportedFormats()));
        System.out.print("Enter format (json/csv): ");

        String format = scanner.nextLine().trim().toLowerCase();

        if (!context.supportsFormat(format)) {
            System.out.println("Unsupported format: " + format);
            return;
        }

        System.out.print("Enter filename (without extension): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            filename = "todos";
        }

        System.out.println("\nImport strategies:");
        System.out.println("1. REPLACE - Clear all existing todos");
        System.out.println("2. APPEND - Add to existing todos");
        System.out.println("3. MERGE_BY_ID - Update existing, add new");
        System.out.print("Choose strategy [3]: ");

        String strategyChoice = scanner.nextLine().trim();

        try {
            switch (strategyChoice) {
                case "1" -> context.importData(format, filename + "." + format,
                                              ImportStrategies.REPLACE);
                case "2" -> context.importData(format, filename + "." + format,
                                              ImportStrategies.APPEND);
                default -> context.importData(format, filename + "." + format,
                                             ImportStrategies.mergeById());
            }

            todos = new ArrayList<>(context.getData());
            System.out.println("Todos imported successfully! Total: " + todos.size());

        } catch (Exception e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }
}
