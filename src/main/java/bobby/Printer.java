package bobby;

import bobby.task.Task;

import java.util.ArrayList;

public class Printer {
    public static void printBanner() {
        System.out.println(" ____        _     _           \n"
                + "| __ )  ___ | |__ | |__  _   _ \n"
                + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
                + "| |_) | (_) | |_) | |_) | |_| |\n"
                + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
                + "                         |___/ \n");
    }

    public static void printWelcomeMessage() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "Hi! I'm Bobby.\n"
                + "What can I do for you?\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    public static void printGoodbyeMessage() {
        System.out.println("Bye Bye!");
    }

    public static void printErrorMessage(String message) {
        System.out.println("ERROR: " + message);
    }

    public static void printMarkedTask(Task task) {
        System.out.println("Good, this task is done: " + task);
    }

    public static void printAddedTask(Task task, int taskCount) {
        System.out.println("added: \n\t" + task
                + "\nYou now have " + taskCount + " pending tasks.");
    }

    public static void printDeletedTask(Task task, int taskCount) {
        System.out.println("removed: \n\t" + task
                + "\nYou now have " + taskCount + " pending tasks.");
    }

    public static void printUnmarkedTask(Task task) {
        System.out.println("Okay, this task is not done: " + task);
    }

    public static void printTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("So empty...");
        } else {
            System.out.println("Here are your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
            }
        }
    }
}
