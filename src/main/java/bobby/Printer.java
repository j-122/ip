package bobby;

import bobby.task.Task;

import java.util.ArrayList;

public class Printer {
    private static final String BORDER = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    private static void printWithBorder(String message) {
        System.out.println(BORDER);
        System.out.println(indented(message));
        System.out.println(BORDER);
    }

    private static String indented(String message) {
        return "\t" + message.replace("\n", "\n\t");
    }

    public static void printBanner() {
        System.out.println(
                  " ____        _     _           \n"
                + "| __ )  ___ | |__ | |__  _   _ \n"
                + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
                + "| |_) | (_) | |_) | |_) | |_| |\n"
                + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
                + "                         |___/ \n"
        );
    }

    public static void printWelcomeMessage() {
        printWithBorder("Hi! I'm Bobby.\nWhat can I do for you?\n");
    }

    public static void printGoodbyeMessage() {
        printWithBorder("Bye Bye!");
    }

    public static void printErrorMessage(String message) {
        printWithBorder("ERROR: " + message);
    }

    public static void printMarkedTask(Task task) {
        printWithBorder("Good, this task is done: " + task);
    }

    public static void printUnmarkedTask(Task task) {
        printWithBorder("Okay, this task is not done: " + task);
    }

    public static void printAddedTask(Task task, int taskCount) {
        printWithBorder("added: \n\t" + task
                + "\nYou now have " + taskCount + " pending tasks.");
    }

    public static void printDeletedTask(Task task, int taskCount) {
        printWithBorder("removed: \n\t" + task
                + "\nYou now have " + taskCount + " pending tasks.");
    }

    public static void printTaskList(ArrayList<Task> tasks) {
        String message;
        if (tasks.isEmpty()) {
            message = "So empty...";
        } else {
            message = "Here are your tasks:\n";
            for (int i = 0; i < tasks.size(); i++) {
                message += "\t" + (i + 1) + ". " + tasks.get(i) + "\n";
            }
        }

        printWithBorder(message);
    }
}
