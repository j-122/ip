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
        String[] arguments = inputLine.split(" ", 2);
        boolean isRunning = true;

        switch (arguments[0]) {
            case EXIT_COMMAND -> isRunning = false;
            case LIST_COMMAND -> showAllTasks();
            case MARK_COMMAND -> handleTaskMarking(arguments[1]);
            case UNMARK_COMMAND -> handleTaskUnmarking(arguments[1]);
            case TODO_KEYWORD -> addTodo(arguments[1]);
            case DEADLINE_KEYWORD -> addDeadline(arguments[1]);
            case EVENT_KEYWORD -> addEvent(arguments[1]);
            default -> System.out.println("Invalid input. Please try again.");
        }

        return isRunning;
    }

    private static void initTaskList() {
        tasks = new Task[MAX_TASK_COUNT];
        taskCount = 0;
    }

    private static void showAllTasks() {
        System.out.println("Here are your tasks:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
    }

    private static void handleTaskMarking(String index) {
        int taskIndex = Integer.parseInt(index) - 1;
        tasks[taskIndex].markAsDone();

        System.out.println("Good, this task is done: " + tasks[taskIndex]);
    }

    private static void handleTaskUnmarking(String index) {
        int taskIndex = Integer.parseInt(index) - 1;
        tasks[taskIndex].markAsNotDone();

        System.out.println("Okay, this task is not done: " + tasks[taskIndex]);
    }
    
    private static void addTodo(String description) {
        registerNewTask(new Todo(description));
    }

    private static void addDeadline(String description) {
        String[] args = description.split("/");
        registerNewTask(new Deadline(args[0].strip(), args[1]));
    }

    private static void addEvent(String description) {
        String[] args = description.split("/");
        registerNewTask(new Event(args[0].strip(), args[1].strip(), args[2]));
    }

    private static void registerNewTask(Task newTask) {
        tasks[taskCount] = newTask;
        taskCount++;

        System.out.println("added: \n\t" + newTask);
        showTaskCount();
    }

    private static void showTaskCount() {
        System.out.println("You now have " + taskCount + " pending tasks.");
    }
}