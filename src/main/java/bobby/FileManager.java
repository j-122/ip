package bobby;

import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager {
    private static final String FILE_PATH = "./data/bobby.txt";
    private static final String DIR_PATH = "./data";

    public FileManager() {
        try {
            createFileIfNeeded();
        } catch (IOException e) {
            System.out.println("ERROR: Unable to create data file.");
        }
    }

    private void createFileIfNeeded() throws IOException {
        File directory = new File(DIR_PATH);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.createNewFile();
        }
    }


    public int loadFile(Task[] tasks)  {
        File file = new File(FILE_PATH);

        int taskCount = 0;
        try {
            taskCount = readFileContents(file, tasks);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found.");
        }

        return taskCount;
    }

    private int readFileContents(File file, Task[] tasks) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            tasks[taskCount] = convertLineToTask(scanner.nextLine());
            taskCount++;
        }

        return taskCount;
    }

    public void addTaskToFile(Task task) throws IOException {
        FileWriter fw = new FileWriter(FILE_PATH, true);
        fw.write(convertTaskToFileFormat(task));
        fw.close();
    }

    public void saveFile(Task[] tasks, int taskCount) throws IOException {
        FileWriter fw = new FileWriter(FILE_PATH);
        for (int i = 0; i < taskCount; i++) {
            fw.write(convertTaskToFileFormat(tasks[i]));
        }

        fw.close();
    }


    private Task convertLineToTask(String line) {
        String[] args = line.split(" \\| ");
        boolean isDone = args[1].equals("1");
        Task task = null;

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
            return "T"
                    + statusAndDescription
                    + System.lineSeparator();
        } else if (task instanceof Deadline deadline) {
            return "D"
                    + statusAndDescription
                    + " | "
                    + deadline.getBy()
                    + System.lineSeparator();
        } else if (task instanceof Event event) {
            return "E"
                    + statusAndDescription
                    + " | "
                    + event.getStart()
                    + " | "
                    + event.getEnd()
                    + System.lineSeparator();
        }

        return "";
    }
}