package bobby;

import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManager {
    private static final String FILE_PATH = "./data/bobby.txt";
    private static final String DIR_PATH = "./data";

    public Task[] loadFile()  {
        File file = new File(FILE_PATH);
        Task[] tasks = new Task[100];

        if (!file.exists()) {
            return tasks;
        }

        try {
            readFileContents(file, tasks);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found.");
        }

        return tasks;
    }

    private void readFileContents(File file, Task[] tasks) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int taskCount = 0;

        while (scanner.hasNext()) {
            tasks[taskCount] = convertLineToTask(scanner.nextLine());
            taskCount++;
        }
    }

    private Task convertLineToTask(String line) {
        String[] args = line.split(" \\| ");
        boolean isDone = (args[1] == "1");
        Task task;

        switch (args[0]) {
            case "T" -> task = new Todo(args[2], isDone);
            case "D" -> task = new Deadline(args[2], isDone, args[3]);
            case "E" -> task = new Event(args[2], isDone, args[3], args[4]);
            default -> System.out.println("ERROR: This line cannot be converted to a task.");
        }

        return task;
    }

    private String convertTaskToFileFormat(Task task) {
        int status = (task.getStatus())? 1 : 0;
        String statusAndDescription = " | " + status + " | " + task.getTaskDescription();

        if (task instanceof Todo) {
            return "T" + statusAndDescription;
        } else if (task instanceof Deadline deadline) {
            return "D" + statusAndDescription + " | " + deadline.getBy();
        } else if (task instanceof Event event) {
            return "E" + statusAndDescription + " | " + event.getStart() + " | " + event.getEnd();
        }

        return "";
    }
}