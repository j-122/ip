package bobby;

import bobby.exception.InvalidTaskException;
import bobby.exception.TaskNotFoundException;
import bobby.parser.Parser;
import bobby.storage.Storage;
import bobby.ui.Ui;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.Todo;
import bobby.taskmanager.TaskManager;


public class Bobby {
    private static TaskManager taskManager;
    private static Ui ui;
    private static Storage storage;

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
            tasksManager = new TaskManager(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            task = new TaskList();
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


    private static void greetUser() {
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

    private static void showAllTasks() {
        ui.showTaskList(taskManager.getTasks());
    }

    private static void handleTaskMarking(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        taskManager.markTask(taskNumber);

        ui.showMarkedTask(taskManager.getTask(taskNumber));
    }

    private static void handleTaskUnmarking(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        taskManager.unmarkTask(taskNumber);

        ui.showUnmarkedTask(taskManager.getTask(taskNumber));
    }

    private static void handleTaskDeletion(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        Task deletedTask = taskManager.deleteTask(taskNumber);

        ui.showDeletedTask(deletedTask, taskManager.getTaskCount());
    }


    private static void addTodo(String args) {
        Todo todo = Parser.parseTodo(args);
        registerTask(todo);
    }

    private static void addDeadline(String args) {
        Deadline deadline = Parser.parseDeadline(args);
        registerTask(deadline);
    }


    private static void addEvent(String args) {
        Event event = Parser.parseEvent(args);
        registerTask(event);
    }

    private static void registerTask(Task task) {
        taskManager.addTask(task);
        ui.showAddedTask(task, taskManager.getTaskCount());
    }
}