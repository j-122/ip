import java.util.Scanner;

public class Bobby {
    public static void main(String[] args) {
        String banner = " ____        _     _           \n"
                + "| __ )  ___ | |__ | |__  _   _ \n"
                + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
                + "| |_) | (_) | |_) | |_) | |_| |\n"
                + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
                + "                         |___/ \n";
        String greeting = "____________________________________________________________\n" +
                "Hi! I'm Bobby.\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n";

        String exit = "Bye Bye!\n";

        System.out.println(banner + greeting);

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();

        final String EXIT_COMMAND = "bye";
        final String LIST_COMMAND = "list";
        final String MARK_COMMAND = "mark";
        final String UNMARK_COMMAND = "unmark";

        Task[] tasks = new Task[100];
        int numOfTasks = 0;

        while (!inputLine.equals(EXIT_COMMAND)) {
            if (inputLine.equals(LIST_COMMAND)) {
                System.out.println("Here are your tasks:");
                for (int i = 0; i < numOfTasks; i++) {
                    System.out.println(String.valueOf(i + 1)
                            + ". ["
                            + tasks[i].getStatusIcon()
                            + "] "
                            + tasks[i].getTaskDescription());
                }
            } else if (inputLine.startsWith(MARK_COMMAND)) {
                String[] parts = inputLine.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Good, this task is done:\n\t[X] " + tasks[taskIndex].getTaskDescription());
            } else if (inputLine.startsWith(UNMARK_COMMAND)) {
                String[] parts = inputLine.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("Okay, this task is not done:\n\t[ ] " + tasks[taskIndex].getTaskDescription());
            } else {
                tasks[numOfTasks] = new Task(inputLine);
                numOfTasks++;
                System.out.println("added " + inputLine);
            }

            inputLine = scanner.nextLine();
        }

        System.out.println(exit);
    }
}
