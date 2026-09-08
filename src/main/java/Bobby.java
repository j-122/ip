import java.util.Scanner;

public class Bobby {
    private static final int MAX_TASK_COUNT = 100;

    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String TODO_KEYWORD = "todo";
    private static final String DEADLINE_KEYWORD = "deadline";
    private static final String EVENT_KEYWORD = "event";

    private static Task[] tasks;
    private static int taskCount;


    public static void main(String[] args) {
        greetUser();
        beginInputProcessing();
        sayGoodbye();
    }


    private static void greetUser() {
        System.out.println(" ____        _     _           \n"
                + "| __ )  ___ | |__ | |__  _   _ \n"
                + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
                + "| |_) | (_) | |_) | |_) | |_| |\n"
                + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
                + "                         |___/ \n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "Hi! I'm Bobby.\n"
                + "What can I do for you?\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void sayGoodbye() {
        System.out.println("Bye Bye!");
    }

    private static void beginInputProcessing() {
        initTaskList();

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

        if (inputLine == null || inputLine.isBlank()) {
            System.out.println("ERROR: No command received. Please enter a command.");
            return isRunning;
        }

        String[] arguments = inputLine.strip().split(" ", 2);

        try {
            switch (arguments[0]) {
                case EXIT_COMMAND -> isRunning = false;
                case LIST_COMMAND -> showAllTasks();
                case MARK_COMMAND -> handleTaskMarking(arguments);
                case UNMARK_COMMAND -> handleTaskUnmarking(arguments);
                case TODO_KEYWORD -> addTodo(arguments);
                case DEADLINE_KEYWORD -> addDeadline(arguments);
                case EVENT_KEYWORD -> addEvent(arguments);
                default -> printErrorMessage("No such command. Try again.");
            }
        } catch (NumberFormatException e) {
            printErrorMessage("Task number must a valid number...");
        } catch (TaskNotFoundException | InvalidTaskException e) {
            printErrorMessage(e.getMessage());
        }

        return isRunning;
    }

    private static void printErrorMessage(String message) {
        System.out.println("ERROR: " + message);
    }

    private static void initTaskList() {
        tasks = new Task[MAX_TASK_COUNT];
        taskCount = 0;
    }

    private static void showAllTasks() {
        if (taskCount < 1) {
            System.out.println("So empty...");
        } else {
            System.out.println("Here are your tasks:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
    }

    private static void handleTaskMarking(String[] args) {
        if (args.length != 2) {
            throw new TaskNotFoundException("Task number is missing...");
        }

        int taskIndex = Integer.parseInt(args[1].strip()) - 1;
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new TaskNotFoundException("You have " + taskCount + " tasks. Please pick within the limits...");
        }

        tasks[taskIndex].markAsDone();
        System.out.println("Good, this task is done: " + tasks[taskIndex]);
    }

    private static void handleTaskUnmarking(String[] args) {
        if (args.length != 2) {
            throw new TaskNotFoundException("Task number is missing...");
        }

        int taskIndex = Integer.parseInt(args[1].strip()) - 1;
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new TaskNotFoundException("You have " + taskCount + " tasks. Please pick within the limits...");
        }

        tasks[taskIndex].markAsNotDone();
        System.out.println("Okay, this task is not done: " + tasks[taskIndex]);
    }
    
    private static void addTodo(String[] args) {
        if (args.length != 2) {
            throw new InvalidTaskException("Task description is missing...");
        }

        registerNewTask(new Todo(args[1].strip()));
    }

    private static void addDeadline(String[] args) {
        if (args.length != 2) {
            throw new InvalidTaskException("Task description is missing...");
        }

        String[] contents = args[1].split("/");
        if (contents.length != 2) {
            throw new InvalidTaskException("Task is missing a deadline. Format: <description> /<deadline>");
        }

        registerNewTask(new Deadline(contents[0].strip(), contents[1].strip()));
    }

    private static void addEvent(String[] args) {
        if (args.length != 2) {
            throw new InvalidTaskException("Task description is missing...");
        }

        String[] contents = args[1].split("/");
        if (contents.length != 3) {
            throw new InvalidTaskException("Task is missing a timeframe. Format: <description> /<start> /<end>");
        }

        registerNewTask(new Event(contents[0].strip(), contents[1].strip(), contents[2].strip()));
    }

    private static void registerNewTask(Task newTask) {
        if (taskCount >= MAX_TASK_COUNT) {
            throw new InvalidTaskException("You have reached the limit on number of tasks.");
        }

        if (newTask.getTaskDescription().isBlank()) {
            throw new InvalidTaskException("Task description is missing...");
        }

        tasks[taskCount] = newTask;
        taskCount++;

        System.out.println("added: \n\t" + newTask + "\nYou now have " + taskCount + " pending tasks.");
    }
}