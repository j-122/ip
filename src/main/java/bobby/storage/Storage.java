package bobby.storage;

import bobby.exception.StorageException;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;


public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;

        try {
            createFileIfNeeded();
        } catch (IOException e) {
            throw new StorageException("Unable to create data file.");
        }
    }

    private void createFileIfNeeded() throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();

        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public ArrayList<Task> loadFile()  {
        File file = new File(filePath);
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            readFileContentsIntoTaskList(file, tasks);
        } catch (FileNotFoundException e) {
            throw new StorageException("File not found.");
        }

        return tasks;
    }

    private void readFileContentsIntoTaskList(File file, ArrayList<Task> tasks) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            tasks.add(convertLineToTask(scanner.nextLine()));
        }
    }

    public void saveFile(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                fw.write(convertTaskToFileFormat(task));
            }

            fw.close();
        } catch (IOException e) {
            throw new StorageException("Something happened while saving file");
        }
    }


    // Format conversion
    private Task convertLineToTask(String line) {
        String[] args = line.split(" \\| ");
        Task task;

        try {
            boolean isDone = checkStatus(args[1]);
            switch (args[0]) {
                case "T" -> task = new Todo(args[2], isDone);
                case "D" -> task = new Deadline(args[2], isDone, args[3]);
                case "E" -> task = new Event(args[2], isDone, args[3], args[4]);
                default -> throw new StorageException("This line cannot be converted to a task.");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new StorageException("Storage file contains invalid task data");
        }

        return task;
    }

    private boolean checkStatus(String arg) {
        if (arg.equals("1")) {
            return true;
        }

        if (arg.equals("0")) {
            return false;
        }

        throw new StorageException("Invalid task status in storage file");
    }

    private String convertTaskToFileFormat(Task task) {
        int status = (task.getStatus())? 1 : 0;
        String statusAndDescription = " | " + status + " | " + task.getTaskDescription();
        String line;

        if (task instanceof Todo) {
            line = "T"
                    + statusAndDescription;
        } else if (task instanceof Deadline deadline) {
            line = "D"
                    + statusAndDescription
                    + " | "
                    + deadline.getBy();
        } else if (task instanceof Event event) {
            line = "E"
                    + statusAndDescription
                    + " | "
                    + event.getStart()
                    + " | "
                    + event.getEnd();
        } else {
            throw new StorageException("Unable to convert task to file format");
        }

        return line + System.lineSeparator();
    }
}