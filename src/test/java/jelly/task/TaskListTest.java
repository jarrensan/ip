package jelly.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskListTest {
    private ArrayList<TaskTest> tasks;

    public TaskListTest(ArrayList<TaskTest> tasks) {
        this.tasks = tasks;
    }

    public TaskListTest() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(TaskTest task) {
        this.tasks.add(task);
    }

    public EventTest addEvent(String desc, LocalDate from, LocalDate to) {
        EventTest event = new EventTest(desc, from, to);
        this.tasks.add(event);
        return event;
    }

    public TodoTest addTodo(String description) {
        TodoTest todo = new TodoTest(description);
        this.tasks.add(todo);
        return todo;
    }

    public DeadlineTest addDeadline(String desc, LocalDate by) {
        DeadlineTest deadline = new DeadlineTest(desc, by);
        this.tasks.add(deadline);
        return deadline;
    }

    public TaskTest removeTask(int index) {
        return this.tasks.remove(index);
    }

    public TaskTest markTask(int index) {
        TaskTest task = tasks.get(index);
        task.markDone();
        return task;
    }

    public TaskTest unmarkTask(int index) {
        TaskTest task = tasks.get(index);
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
        for (TaskTest t : tasks) {
            sb.append(t.toSave()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            TaskTest task = tasks.get(i);
            String isDone = " ";
            sb.append("\n ").append(i + 1).append(".").append(task.toString());
        }
        return sb.toString();
    }



}
