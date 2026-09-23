package bobby;

import bobby.exception.BobbyException;
import bobby.exception.InvalidTaskException;
import bobby.exception.StorageException;
import bobby.exception.TaskNotFoundException;
import bobby.parser.Parser;
import bobby.storage.Storage;
import bobby.ui.Ui;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.Todo;
import bobby.taskmanager.TaskManager;

import java.io.IOException;


public class Bobby {
    private TaskManager taskManager;
    private Ui ui;
    private Storage storage;

    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";

    private static final String TODO_KEYWORD = "todo";
    private static final String DEADLINE_KEYWORD = "deadline";
    private static final String EVENT_KEYWORD = "event";

    public Bobby(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            taskManager = new TaskManager(storage.loadFile());
        } catch (StorageException e) {
            ui.showErrorMessage(e.getMessage());
            taskManager = new TaskManager();
        }
    }

    public void run() {
        greetUser();

        boolean isRunning = true;
        while (isRunning) {
            String inputLine = ui.getInput();
            isRunning = handleInput(inputLine);
        }

        sayGoodbye();
    }

    public static void main(String[] args) {
        new Bobby("data/tasks.txt").run();
    }


    private void greetUser() {
        ui.showBanner();
        ui.showWelcomeMessage();
    }

    private void sayGoodbye() {
        ui.showGoodbyeMessage();
    }

    private boolean handleInput(String inputLine) {
        boolean isRunning = true;

        if (inputLine.isBlank()) {
            ui.showErrorMessage("No command received. Please enter a command.");
            return isRunning;
        }

        String command = Parser.getCommand(inputLine);
        String arguments = Parser.getArguments(inputLine);

        try {
            switch (command) {
                case EXIT_COMMAND -> isRunning = false;
                case LIST_COMMAND -> showAllTasks();
                case MARK_COMMAND -> handleTaskMarking(arguments);
                case UNMARK_COMMAND -> handleTaskUnmarking(arguments);
                case DELETE_COMMAND -> handleTaskDeletion(arguments);
                case TODO_KEYWORD -> addTodo(arguments);
                case DEADLINE_KEYWORD -> addDeadline(arguments);
                case EVENT_KEYWORD -> addEvent(arguments);
                default -> ui.showErrorMessage("No such command. Try again.");
            }
        } catch (NumberFormatException e) {
            ui.showErrorMessage("Task number must a valid number...");
        } catch (TaskNotFoundException | InvalidTaskException e) {
            ui.showErrorMessage(e.getMessage());
        }

        return isRunning;
    }

    private void showAllTasks() {
        ui.showTaskList(taskManager.getTasks());
    }

    private void handleTaskMarking(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        taskManager.markTask(taskNumber);

        ui.showMarkedTask(taskManager.getTask(taskNumber));
        applyFileChanges();
    }

    private void handleTaskUnmarking(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        taskManager.unmarkTask(taskNumber);

        ui.showUnmarkedTask(taskManager.getTask(taskNumber));
        applyFileChanges();
    }

    private void handleTaskDeletion(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        Task deletedTask = taskManager.deleteTask(taskNumber);

        ui.showDeletedTask(deletedTask, taskManager.getTaskCount());
        applyFileChanges();
    }


    private void addTodo(String args) {
        Todo todo = Parser.parseTodo(args);
        registerTask(todo);
    }

    private void addDeadline(String args) {
        Deadline deadline = Parser.parseDeadline(args);
        registerTask(deadline);
    }


    private void addEvent(String args) {
        Event event = Parser.parseEvent(args);
        registerTask(event);
    }

    private void registerTask(Task task) {
        taskManager.addTask(task);
        ui.showAddedTask(task, taskManager.getTaskCount());
        applyFileChanges();
    }

    private void applyFileChanges() {
        try {
            storage.saveFile(taskManager.getTasks());
        } catch (IOException e) {
            ui.showErrorMessage("Something happened while saving file.");
        }
    }
}