# Todo List Example

A functional todo list manager demonstrating key features of the JMiniApp framework.

## Overview

This example shows how to build a small command-line application that manages a list of tasks (todos). Users can add, view, complete, and delete tasks. The app uses advanced features like saving data in two different file types (JSON and CSV) and merging data when importing.

## Features

* **Add/Delete Tasks**: Full control over your todo items.
* **Filter Tasks**: View only pending or only completed tasks.
* **Data Saving**: Supports saving (Export) and loading (Import) data in **JSON** and **CSV** formats.
* **Smart Data Load**: Includes special modes for importing: replace all data, add to existing data, or 
* **Priority**: Tasks can be marked with High, Medium, or Low priority.
* **Persistent State**: All tasks are automatically saved when you exit the app.

---

## Project Structure


todo/ 
  ├── pom.xml 
  ├── README.md 
  └── src/main/java/com/jminiapp/examples/todo/ 
    ├── TodoAppRunner.java # Setup and launch configuration 
    ├── TodoItem.java # The model for a single task 
    ├── TodoJSONAdapter.java # Handles saving/loading data in JSON format 
    └── TodoCSVAdapter.java # Handles saving/loading data in CSV format


---

## Key Components

### TodoItem
This is the data model for a single task. It holds the task's title, description, completion status, and a unique ID. The unique ID is important for the "smart merge" feature during data imports.

### TodoJSONAdapter & TodoCSVAdapter
These are tools that teach the framework how to read and write your task list data into standard **JSON** files and **CSV** files (like a spreadsheet).

### TodoApp
The main application class. It controls the app's startup, the main menu, and all the actions like adding a task or exporting the list. It tells the framework when to load and save the data.

### TodoAppRunner
The starting point of the application. It tells the framework which main class to run (`TodoApp`) and which file adapters to use (`TodoJSONAdapter` and `TodoCSVAdapter`).

---

## Building and Running

### Prerequisites
* **Java 17** or newer
* **Maven 3.6** or newer

### Build the project

First, build the entire project from the **root directory** of your repository:

```bash
mvn clean install

```

 ### Run Todo

cd examples/todo
mvn exec:java


