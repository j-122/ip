package bobby;

import bobby.exception.InvalidTaskException;
import bobby.exception.TaskNotFoundException;
import bobby.task.Task;

import java.util.ArrayList;

public class TaskManager {
    private static final int MAX_TASK_COUNT = 100;
    private ArrayList<Task> tasks;

    TaskManager() {
        tasks = new ArrayList<>();
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
        System.out.println("added: \n\t" + task + "\nYou now have " + tasks.size() + " pending tasks.");
    }

    public void deleteTask(int taskNumber) {
        Task task = this.getTask(taskNumber);
        tasks.remove(task);

        System.out.println("removed: \n\t" + task + "\nYou now have " + tasks.size() + " pending tasks.");

    }

    public void printTaskList() {
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
