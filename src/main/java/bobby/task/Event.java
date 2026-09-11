package bobby.task;

public class Event extends Task {
    private String start;
    private String end;

    public Event(String description, String start, String end) {
        this(description, false, start, end);
    }

    public Event(String description, boolean isDone, String start, String end) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " -> Start: " + start + " ~ End: " + end;
    }
}