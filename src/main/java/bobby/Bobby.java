package bobby;

import bobby.exception.InvalidTaskException;
import bobby.exception.TaskNotFoundException;
import bobby.parser.Parser;
import bobby.printer.Printer;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.Todo;
import bobby.taskmanager.TaskManager;

import java.util.Scanner;

public class Bobby {
    private static TaskManager taskManager;

    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";

    private static final String TODO_KEYWORD = "todo";
    private static final String DEADLINE_KEYWORD = "deadline";
    private static final String EVENT_KEYWORD = "event";

    public static void main(String[] args) {
        greetUser();
        beginInputProcessing();
        sayGoodbye();
    }


    private static void greetUser() {
        Printer.printBanner();
        Printer.printWelcomeMessage();
    }

    private static void sayGoodbye() {
        Printer.printGoodbyeMessage();
    }


    private static void beginInputProcessing() {
        taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        String inputLine;
        boolean isRunning = true;

        while (isRunning) {
            inputLine = scanner.nextLine();
            isRunning = handleInput(inputLine);
        }
    }

    private static boolean handleInput(String inputLine) {
        boolean isRunning = true;

        if (inputLine.isBlank()) {
            Printer.printErrorMessage("No command received. Please enter a command.");
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
                default -> Printer.printErrorMessage("No such command. Try again.");
            }
        } catch (NumberFormatException e) {
            Printer.printErrorMessage("Task number must a valid number...");
        } catch (TaskNotFoundException | InvalidTaskException e) {
            Printer.printErrorMessage(e.getMessage());
        }

        return isRunning;
    }

    private static void showAllTasks() {
        Printer.printTaskList(taskManager.getTasks());
    }

    private static void handleTaskMarking(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        taskManager.markTask(taskNumber);

        Printer.printMarkedTask(taskManager.getTask(taskNumber));
    }

    private static void handleTaskUnmarking(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        taskManager.unmarkTask(taskNumber);

        Printer.printUnmarkedTask(taskManager.getTask(taskNumber));
    }

    private static void handleTaskDeletion(String number) {
        int taskNumber = Parser.getTaskNumber(number);
        Task deletedTask = taskManager.deleteTask(taskNumber);

        Printer.printDeletedTask(deletedTask, taskManager.getTaskCount());
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
        Printer.printAddedTask(task, taskManager.getTaskCount());
    }
}