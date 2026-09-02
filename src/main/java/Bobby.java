import java.util.Scanner;

public class Bobby {
    private static Task[] tasks;
    private static int taskCount;

    private static final int MAX_TASK_COUNT = 100;

    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String TODO_KEYWORD = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";

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
                + "____________________________________________________________\n"
                + "Hi! I'm Bobby.\n"
                + "What can I do for you?\n"
                + "____________________________________________________________\n");
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
        switch (arguments[0]) {
            case EXIT_COMMAND -> {
                return false;
            }
            case MARK_COMMAND -> {
                String[] parts = inputLine.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsDone();

                System.out.println("Good, this task is done:\n\t[X] "
                        + tasks[taskIndex].getTaskDescription());
            }
            case UNMARK_COMMAND -> {
                String[] parts = inputLine.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsNotDone();

                System.out.println("Okay, this task is not done:\n\t[ ] "
                        + tasks[taskIndex].getTaskDescription());
            }
            case LIST_COMMAND -> {
                System.out.println("Here are your tasks:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.print(i + 1);
                    System.out.println(". " + tasks[i]);
                }
            }
            case TODO_KEYWORD -> {
                tasks[taskCount] = new Todo(arguments[1]);
                System.out.println("added " + tasks[taskCount]);
                taskCount++;
            }
            case DEADLINE_COMMAND -> {
                String[] args = arguments[1].split("/");
                tasks[taskCount] = new Deadline(args[0].strip(), args[1]);
                System.out.println("added " + tasks[taskCount]);
                taskCount++;

            }
            case EVENT_COMMAND -> {
                String[] args = arguments[1].split("/");
                tasks[taskCount] = new Event(args[0].strip(), args[1].strip(), args[2]);
                System.out.println("added " + tasks[taskCount]);
                taskCount++;
            }
            default -> System.out.println("Invalid input. Please try again.");
        }
        return true;
    }

    private static void initTaskList() {
        tasks = new Task[MAX_TASK_COUNT];
        taskCount = 0;
    }
}
