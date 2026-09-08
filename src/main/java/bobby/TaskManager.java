package bobby;

import bobby.exception.InvalidTaskException;
import bobby.exception.TaskNotFoundException;
import bobby.task.Task;

public class TaskManager {
    private static final int MAX_TASK_COUNT = 100;

    private Task[] tasks;
    private int taskCount;

    TaskManager() {
        tasks = new Task[MAX_TASK_COUNT];
        taskCount = 0;
    }

    public Task getTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new TaskNotFoundException("You have " + taskCount + " tasks. Please pick within the limits...");
        }

        return tasks[taskNumber - 1];
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void addTask(Task task) {
        if (taskCount >= MAX_TASK_COUNT) {
            throw new InvalidTaskException("You have reached the limit on number of tasks.");
        }

        if (task.getTaskDescription().isBlank()) {
            throw new InvalidTaskException("Task description is missing...");
        }

        tasks[taskCount] = task;
        taskCount++;

        System.out.println("added: \n\t" + task + "\nYou now have " + taskCount + " pending tasks.");
    }

    public void printTaskList() {
        if (taskCount < 1) {
            System.out.println("So empty...");
        } else {
            System.out.println("Here are your tasks:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
    }
}
