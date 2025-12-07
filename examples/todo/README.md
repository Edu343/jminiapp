# Todo List Example

A comprehensive todo list manager demonstrating advanced features of the JMiniApp framework including CRUD operations, multiple format adapters, and import strategies.

## Overview

This example demonstrates how to build a feature-rich mini-app using JMiniApp core. It showcases CRUD operations (Create, Read, Update, Delete), multiple format adapters (JSON and CSV), import strategies, and a more complex data model compared to the counter example.

## Features

- **Add Todos**: Create new todo items with title, description, and priority
- **List Todos**: View all todos or filter by completion status (pending/completed)
- **Complete Todos**: Toggle completion status of any todo item
- **Edit Todos**: Update title, description, and priority of existing todos
- **Delete Todos**: Remove unwanted todo items
- **Export Data**: Save todos to JSON or CSV format
- **Import Data**: Load todos from JSON or CSV with multiple merge strategies
- **Priority Levels**: Organize todos with HIGH, MEDIUM, or LOW priority
- **Persistent State**: All todos are automatically saved between sessions

## Project Structure

```
todo/
├── pom.xml
├── README.md
├── src/main/java/com/jminiapp/examples/todo/
│   ├── TodoApp.java          # Main application class
│   ├── TodoAppRunner.java    # Bootstrap configuration
│   ├── TodoItem.java         # Todo model with Priority enum
│   ├── TodoJSONAdapter.java  # JSON format adapter
│   └── TodoCSVAdapter.java   # CSV format adapter
└── src/main/resources/
    └── Todo.json             # Persistent state file
```

## Key Components

### TodoItem

A comprehensive model class representing a todo item with:
- **id**: Unique identifier (UUID)
- **title**: Todo title
- **description**: Detailed description
- **completed**: Completion status (boolean)
- **priority**: Priority level (HIGH, MEDIUM, LOW)
- **createdAt**: Creation timestamp (ISO format)

Methods:
- `toggleComplete()`: Switch between completed/incomplete
- `toString()`: Formatted display with status and priority symbols

### TodoJSONAdapter

A format adapter that enables JSON import/export for `TodoItem`:
- Implements `JSONAdapter<TodoItem>` from the framework
- Provides automatic JSON serialization using Gson
- Handles all TodoItem fields including nested Priority enum
- Simple implementation requiring only `getstateClass()` method

### TodoCSVAdapter

A custom CSV format adapter that:
- Extends `CSVAdapter<TodoItem>` from the framework
- Implements `getHeader()` for CSV column names
- Implements `toCSV()` for TodoItem to CSV row conversion
- Implements `fromCSV()` for CSV row to TodoItem parsing
- Handles field escaping for commas and quotes

### TodoApp

The main application class that extends `JMiniApp` and implements:
- **initialize()**: Load existing todos or create empty list
- **run()**: Interactive menu loop with 10 different operations
- **shutdown()**: Save todos to context before exit

Features:
- Full CRUD operations (Create, Read, Update, Delete)
- Filtering by status (all, pending, completed)
- Export with format selection (JSON/CSV)
- Import with strategy selection (REPLACE, APPEND, MERGE_BY_ID)
- Input validation and error handling

### TodoAppRunner

Bootstrap configuration that:
- Registers both `TodoJSONAdapter` and `TodoCSVAdapter`
- Configures the app name as "Todo"
- Sets `TodoItem` as the state class
- Launches the application using JMiniAppRunner

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/todo directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the todo example.

### Run the application

**Option 1**: Using Maven exec plugin (from the examples/todo directory)
```bash
cd examples/todo
mvn exec:java
```

**Option 2**: Using the packaged JAR (from the examples/todo directory)
```bash
cd examples/todo
java -jar target/todo-app.jar
```

**Option 3**: From the project root
```bash
cd examples/todo && mvn exec:java
```

## Usage Examples

### Basic Operations

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

Choose an option: 2
```

### Adding a New Todo

```
Choose an option: 1

--- Add New Todo ---
Title: Implement user authentication
Description: Add JWT-based authentication to the API
Priority (HIGH/MEDIUM/LOW) [MEDIUM]: HIGH

Todo added successfully!
[✓] !!! Implement user authentication - Add JWT-based authentication to the API
    ID: 7c9e6679-7425-40de-944b-e07fc1f90ae7 | Created: 2024-01-15T14:30:00
```

### Listing Todos

```
Choose an option: 3

--- PENDING Todos ---

1. [ ] !!! Complete JMiniApp tutorial - Follow the step-by-step guide to build the todo list app
    ID: 550e8400-e29b-41d4-a716-446655440000 | Created: 2024-01-15T10:30:00

2. [ ] !! Test JSON export - Export the todo list to a JSON file
    ID: 550e8400-e29b-41d4-a716-446655440001 | Created: 2024-01-15T11:00:00

Total: 2 todo(s)
```

### Marking Todo as Complete

```
Choose an option: 5

--- ALL Todos ---
[Lists all todos...]

Enter todo number to toggle completion: 1

Todo updated:
[✓] !!! Complete JMiniApp tutorial - Follow the step-by-step guide to build the todo list app
    ID: 550e8400-e29b-41d4-a716-446655440000 | Created: 2024-01-15T10:30:00
```

### Editing a Todo

```
Choose an option: 6

--- ALL Todos ---
[Lists all todos...]

Enter todo number to edit: 2

Editing: Test JSON export
New title [Test JSON export]: Test both JSON and CSV export
New description [Export the todo list to a JSON file]: Test exporting to both formats
New priority (HIGH/MEDIUM/LOW) [MEDIUM]: HIGH

Todo updated successfully!
[✓] !!! Test both JSON and CSV export - Test exporting to both formats
    ID: 550e8400-e29b-41d4-a716-446655440001 | Created: 2024-01-15T11:00:00
```

### Exporting to JSON

```
Choose an option: 8

--- Export Todos ---
Supported formats: json, csv
Enter format (json/csv): json
Enter filename (without extension): my-todos

Todos exported successfully to my-todos.json
```

**Exported JSON file** (my-todos.json):
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "Complete JMiniApp tutorial",
    "description": "Follow the step-by-step guide to build the todo list app",
    "completed": true,
    "priority": "HIGH",
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "title": "Test JSON export",
    "description": "Export the todo list to a JSON file",
    "completed": false,
    "priority": "MEDIUM",
    "createdAt": "2024-01-15T11:00:00"
  }
]
```

### Exporting to CSV

```
Choose an option: 8

--- Export Todos ---
Supported formats: json, csv
Enter format (json/csv): csv
Enter filename (without extension): todos-backup

Todos exported successfully to todos-backup.csv
```

**Exported CSV file** (todos-backup.csv):
```csv
id,title,description,completed,priority,createdAt
550e8400-e29b-41d4-a716-446655440000,Complete JMiniApp tutorial,Follow the step-by-step guide to build the todo list app,true,HIGH,2024-01-15T10:30:00
550e8400-e29b-41d4-a716-446655440001,Test JSON export,Export the todo list to a JSON file,false,MEDIUM,2024-01-15T11:00:00
```

### Importing with Strategies

```
Choose an option: 9

--- Import Todos ---
Supported formats: json, csv
Enter format (json/csv): json
Enter filename (without extension): my-todos

Import strategies:
1. REPLACE - Clear all existing todos
2. APPEND - Add to existing todos
3. MERGE_BY_ID - Update existing, add new
Choose strategy [3]: 3

Todos imported successfully! Total: 5
```

**Import Strategy Details**:
- **REPLACE** (1): Removes all current todos and replaces with imported data
- **APPEND** (2): Adds imported todos to existing list (may create duplicates)
- **MERGE_BY_ID** (3): Smart merge - updates existing todos by ID, adds new ones

### Deleting a Todo

```
Choose an option: 7

--- ALL Todos ---
[Lists all todos...]

Enter todo number to delete: 3

Deleted: Test CSV import
```

## Data Format Examples

### JSON Format
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "Complete JMiniApp tutorial",
    "description": "Follow the step-by-step guide",
    "completed": false,
    "priority": "HIGH",
    "createdAt": "2024-01-15T10:30:00"
  }
]
```

### CSV Format
```csv
id,title,description,completed,priority,createdAt
550e8400-e29b-41d4-a716-446655440000,Complete JMiniApp tutorial,Follow the step-by-step guide,false,HIGH,2024-01-15T10:30:00
```

## Framework Features Demonstrated

This example showcases:

1. **Multiple Format Adapters**: JSON and CSV support in a single app
2. **Import Strategies**: REPLACE, APPEND, and MERGE_BY_ID for flexible data merging
3. **Complex Data Model**: Multi-field model with enums and timestamps
4. **CRUD Operations**: Full Create, Read, Update, Delete functionality
5. **Data Filtering**: Filter todos by completion status
6. **Lifecycle Management**: Proper use of initialize, run, and shutdown phases
7. **Context API**: Extensive use of JMiniAppContext for data operations
8. **Input Validation**: Error handling and user input validation

## Next Steps

Try extending this example by:
- Adding due dates and reminders
- Implementing todo categories/tags
- Adding search functionality
- Implementing undo/redo operations
- Adding todo archiving
- Creating a web UI interface
- Adding user authentication and multi-user support
- Implementing recurring todos
- Adding file attachments to todos
- Creating visual statistics and reports

---

**Author**: Claude Code Assistant
**Framework**: JMiniApp v1.0.0-SNAPSHOT
**License**: MIT
