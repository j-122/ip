package bobby.task;

public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        this(description, false, by);
    }

    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " -> by: " + by;
    }
}
