package jelly.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void updateDescription(String newDesc) {
        this.description = newDesc;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getSaveStatusIcon() {
        return (isDone ? "1" : "0");
    }

    public void markDone() {
        isDone = true;
    }

    public void unmarkDone() {
        isDone = false;
    }

    /**
     * Returns string data of task to be saved to file.
     *
     * @return String data of task.
     */
    public String toSave() {
        return getSaveStatusIcon() + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
