package jelly.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Event addEvent(String desc, LocalDate from, LocalDate to) {
        Event event = new Event(desc, from, to);
        this.tasks.add(event);
        return event;
    }

    public Todo addTodo(String description) {
        Todo todo = new Todo(description);
        this.tasks.add(todo);
        return todo;
    }

    public Deadline addDeadline(String desc, LocalDate by) {
        Deadline deadline = new Deadline(desc, by);
        this.tasks.add(deadline);
        return deadline;
    }

    public Task removeTask(int index) {
        return this.tasks.remove(index);
    }

    public Task markTask(int index) {
        Task task = tasks.get(index);
        task.markDone();
        return task;
    }

    public Task unmarkTask(int index) {
        Task task = tasks.get(index);
        task.unmarkDone();
        return task;
    }

    public int getSize() {
        return this.tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        for (Task t : tasks) {
            sb.append(t.toSave()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String isDone = " ";
            sb.append("\n ").append(i + 1).append(".").append(task.toString());
        }
        return sb.toString();
    }



}
