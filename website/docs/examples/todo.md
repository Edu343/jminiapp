---
sidebar_position: 2
---

# Todo List Example Application

A todo list manager demonstrating CRUD operations, multiple format adapters, and import strategies.

**Features:**
- Add, edit, delete todos
- Mark todos as complete/incomplete
- Priority levels (HIGH, MEDIUM, LOW)
- JSON and CSV import/export
- Multiple import strategies (REPLACE, APPEND, MERGE_BY_ID)
- Filter by status (pending/completed)

**Source Code:** [examples/todo](https://github.com/jminiapp/jminiapp/tree/main/examples/todo)

### Key Concepts Demonstrated

- Full CRUD operations (Create, Read, Update, Delete)
- Multiple format adapters (JSON and CSV)
- Import strategies for flexible data merging
- Complex data model with enums and multiple fields
- Data filtering with Java Streams
- Interactive menu system

### Quick Start

```bash
cd examples/todo
mvn clean install
mvn exec:java
```

Or run the packaged JAR:
```bash
java -jar target/todo-app.jar
```

### Code Highlights

**State Model:**
```java
public class TodoItem {
    public enum Priority { LOW, MEDIUM, HIGH }

    private String id;
    private String title;
    private String description;
    private boolean completed;
    private Priority priority;
    private String createdAt;

    public TodoItem() {
        this.id = UUID.randomUUID().toString();
        this.completed = false;
        this.priority = Priority.MEDIUM;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void toggleComplete() {
        this.completed = !this.completed;
    }
}
```

**JSON Adapter:**
```java
public class TodoJSONAdapter implements JSONAdapter<TodoItem> {
    @Override
    public Class<TodoItem> getstateClass() {
        return TodoItem.class;
    }
}
```

**CSV Adapter:**
```java
public class TodoCSVAdapter implements CSVAdapter<TodoItem> {
    @Override
    public String[] getHeader() {
        return new String[]{"id", "title", "description", "completed", "priority", "createdAt"};
    }

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

    @Override
    public TodoItem fromCSV(String[] fields) {
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
```

**Application:**
```java
public class TodoApp extends JMiniApp {
    private List<TodoItem> todos;
    private Scanner scanner;

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

    @Override
    protected void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addTodo();
                case "2" -> listTodos("all");
                case "3" -> completeTodo();
                case "7" -> deleteTodo();
                case "8" -> exportData();
                case "9" -> importData();
                case "0" -> running = false;
            }
        }
    }

    @Override
    protected void shutdown() {
        context.setData(todos);
        System.out.println("Todos saved successfully");
    }
}
```

**Bootstrap:**
```java
public class TodoAppRunner {
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
```

### Usage Example

```
=== Welcome to Todo List Manager ===

Loaded 3 existing todo(s)

--- Todo List Menu ---
1. Add new todo
2. List all todos
3. List pending todos
4. List completed todos
5. Mark todo as complete/incomplete
6. Edit todo
7. Delete todo
8. Export todos
9. Import todos
0. Exit

Choose an option: 1

--- Add New Todo ---
Title: Implement authentication
Description: Add JWT-based auth to the API
Priority (HIGH/MEDIUM/LOW) [MEDIUM]: HIGH

Todo added successfully!
[ ] !!! Implement authentication - Add JWT-based auth to the API
    ID: 7c9e6679-7425-40de-944b-e07fc1f90ae7 | Created: 2024-01-15T14:30:00
```

### Import Strategies

The todo app demonstrates three different import strategies:

**REPLACE**: Clear all existing todos and replace with imported data
```java
context.importData("json", "backup.json", ImportStrategies.REPLACE);
```

**APPEND**: Add imported todos to existing list
```java
context.importData("json", "backup.json", ImportStrategies.APPEND);
```

**MERGE_BY_ID**: Smart merge - update existing todos by ID, add new ones
```java
context.importData("json", "backup.json", ImportStrategies.mergeById());
```

This example demonstrates advanced JMiniApp features including CRUD operations, multiple format adapters, and flexible data import strategies. The framework handles state persistence and file operations while you focus on application logic.

---

**Author:** Manuel Eduardo Del Rio Camacho
