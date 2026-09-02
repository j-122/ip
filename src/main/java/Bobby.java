import java.util.Scanner;

public class Bobby {
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";

    public static void main(String[] args) {
        greetUser();

        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();

        while (!inputLine.equals(EXIT_COMMAND)) {
            if (inputLine.equals(LIST_COMMAND)) {
                System.out.println("Here are your tasks:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1)
                            + ". ["
                            + tasks[i].getStatusIcon()
                            + "] "
                            + tasks[i].getTaskDescription());
                }
            } else if (inputLine.startsWith(MARK_COMMAND)) {
                String[] parts = inputLine.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsDone();

                System.out.println("Good, this task is done:\n\t[X] "
                        + tasks[taskIndex].getTaskDescription());
            } else if (inputLine.startsWith(UNMARK_COMMAND)) {
                String[] parts = inputLine.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsNotDone();

                System.out.println("Okay, this task is not done:\n\t[ ] "
                        + tasks[taskIndex].getTaskDescription());
            } else {
                tasks[taskCount] = new Task(inputLine);
                taskCount++;

                System.out.println("added " + inputLine);
            }

            inputLine = scanner.nextLine();
        }

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
}
