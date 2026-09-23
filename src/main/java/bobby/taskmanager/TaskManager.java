package bobby.taskmanager;

import bobby.storage.Storage;
import bobby.ui.Ui;
import bobby.exception.InvalidTaskException;
import bobby.exception.TaskNotFoundException;
import bobby.task.Task;

import java.util.ArrayList;
import java.io.IOException;

public class TaskManager {
    private static final int MAX_TASK_COUNT = 100;
    private ArrayList<Task> tasks;

    public TaskManager() {
        this(new ArrayList<>());
    }

    public TaskManager(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Getters
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new TaskNotFoundException("You have " + tasks.size() + " tasks. Please pick within the limits...");
        }

        return tasks.get(taskNumber - 1);
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public void addTask(Task task) {
        if (tasks.size() >= MAX_TASK_COUNT) {
            throw new InvalidTaskException("You have reached the limit on number of tasks.");
        }

        if (task.getTaskDescription().isBlank()) {
            throw new InvalidTaskException("Task description is missing...");
        }

        tasks.add(task);
    }

    public Task deleteTask(int taskNumber) {
        Task task = getTask(taskNumber);
        tasks.remove(taskNumber - 1);

        return task;
    }

    public void markTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.markAsDone();
    }

    public void unmarkTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.markAsNotDone();
    }
}
