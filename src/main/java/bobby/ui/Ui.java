package bobby.ui;

import bobby.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;
    private static final String BORDER = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    private void printWithBorder(String message) {
        System.out.println(BORDER);
        System.out.println(indented(message));
        System.out.println(BORDER);
    }

    private String indented(String message) {
        return "\t" + message.replace("\n", "\n\t");
    }

    public void showBanner() {
        System.out.println(
                  " ____        _     _           \n"
                + "| __ )  ___ | |__ | |__  _   _ \n"
                + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
                + "| |_) | (_) | |_) | |_) | |_| |\n"
                + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
                + "                         |___/ \n"
        );
    }

    public void showWelcomeMessage() {
        printWithBorder("Hi! I'm Bobby.\nWhat can I do for you?\n");
    }

    public void showGoodbyeMessage() {
        printWithBorder("Bye Bye!");
    }

    public void showErrorMessage(String message) {
        printWithBorder("ERROR: " + message);
    }

    public void showMarkedTask(Task task) {
        printWithBorder("Good, this task is done: " + task);
    }

    public void showUnmarkedTask(Task task) {
        printWithBorder("Okay, this task is not done: " + task);
    }

    public void showAddedTask(Task task, int taskCount) {
        printWithBorder("added: \n\t" + task
                + "\nYou now have " + taskCount + " pending tasks.");
    }

    public void showDeletedTask(Task task, int taskCount) {
        printWithBorder("removed: \n\t" + task
                + "\nYou now have " + taskCount + " pending tasks.");
    }

    public void showTaskList(ArrayList<Task> tasks) {
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
