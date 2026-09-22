package bobby.parser;

import bobby.exception.InvalidTaskException;
import bobby.exception.TaskNotFoundException;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Todo;

public class Parser {

    public static String getCommand(String input) {
        return input.strip().split(" ", 2)[0];
    }

    public static String getArguments(String input) {
        String[] parts = input.strip().split(" ", 2);

        if (parts.length < 2) {
            return "";
        }

        return parts[1].strip();
    }

    public static int getTaskNumber(String arg) {
        if (arg.isBlank()) {
            throw new TaskNotFoundException("Task number is missing...");
        }

        return Integer.parseInt(arg.strip());
    }

    public static Todo parseTodo(String todo) {
        if (todo.isBlank()) {
            throw new InvalidTaskException("Task description is missing...");
        }

        return new Todo(todo.strip());
    }

    public static Deadline parseDeadline(String deadline) {
        if (deadline.isBlank()) {
            throw new InvalidTaskException("Task description is missing...");
        }

        String[] contents = deadline.split("/", 2);
        if (contents.length != 2) {
            throw new InvalidTaskException("Task is missing a deadline. Format: <description> /<deadline>");
        }

        return new Deadline(contents[0].strip(), contents[1].strip());
    }

    public static Event parseEvent(String event) {
        if (event.isBlank()) {
            throw new InvalidTaskException("Task description is missing...");
        }

        String[] contents = event.split("/", 3);
        if (contents.length != 3) {
            throw new InvalidTaskException("Task is missing a timeframe. Format: <description> /<start> /<end>");
        }

        return new Event(contents[0].strip(), contents[1].strip(), contents[2].strip());
    }

}
