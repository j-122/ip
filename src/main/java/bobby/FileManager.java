package bobby;

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


public class FileManager {
    private static final String FILE_PATH = "./data/bobby.txt";
    private static final String DIR_PATH = "./data";

    public FileManager() {
        try {
            createFileIfNeeded();
        } catch (IOException e) {
            Printer.printErrorMessage("Unable to create data file.");
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

    public void loadFile(ArrayList<Task> tasks)  {
        File file = new File(FILE_PATH);
        try {
            readFileContentsIntoTaskList(file, tasks);
        } catch (FileNotFoundException e) {
            Printer.printErrorMessage("File not found.");
        }
    }

    private void readFileContentsIntoTaskList(File file, ArrayList<Task> tasks) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            tasks.add(convertLineToTask(scanner.nextLine()));
        }
    }

//    public void appendTaskToFile(Task task) throws IOException {
//        FileWriter fw = new FileWriter(FILE_PATH, true);
//        fw.write(convertTaskToFileFormat(task));
//
//        fw.close();
//    }

    public void saveFile(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(FILE_PATH);
        for (Task task : tasks) {
            fw.write(convertTaskToFileFormat(task));
        }

        fw.close();
    }


    // Format conversion
    private Task convertLineToTask(String line) {
        String[] args = line.split(" \\| ");
        boolean isDone = args[1].equals("1");
        /*
            Need to add error handling here soon
         */
        Task task = null;

        switch (args[0]) {
            case "T" -> task = new Todo(args[2], isDone);
            case "D" -> task = new Deadline(args[2], isDone, args[3]);
            case "E" -> task = new Event(args[2], isDone, args[3], args[4]);
            default -> Printer.printErrorMessage("This line cannot be converted to a task.");
        }

        return task;
    }

    private String convertTaskToFileFormat(Task task) {
        int status = (task.getStatus())? 1 : 0;
        String statusAndDescription = " | " + status + " | " + task.getTaskDescription();
        String line = "";

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
            Printer.printErrorMessage("Unable to convert task to file format");
        }

        return line + System.lineSeparator();
    }
}