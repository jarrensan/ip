package jelly.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds task to existing arraylist of tasks.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns and add Event task to existing arraylist of tasks from specified arguments.
     *
     * @param desc Description of Event task to be added.
     * @param from Start date of Event task.
     * @param to End date of Event task.
     * @return Event task created from specified input arguments.
     */
    public Event addEvent(String desc, LocalDate from, LocalDate to) {
        Event event = new Event(desc, from, to);
        addTask(event);
        return event;
    }

    /**
     * Returns and add Todo task to existing arraylist of tasks from specified arguments.
     *
     * @param desc Description of Todo task to be added.
     * @return Todo task created from specified input arguments.
     */
    public Todo addTodo(String desc) {
        Todo todo = new Todo(desc);
        addTask(todo);
        return todo;
    }

    /**
     * Returns and add Deadline task to existing arraylist of tasks from specified arguments.
     *
     * @param desc Description of Deadline task to be added.
     * @param by Completion date of Deadline task.
     * @return Deadline task created from specified input arguments.
     */
    public Deadline addDeadline(String desc, LocalDate by) {
        Deadline deadline = new Deadline(desc, by);
        addTask(deadline);
        return deadline;
    }

    /**
     * Removes task from existing arraylist of tasks from specified index.
     *
     * @param index Index of task to be removed.
     * @return Task removed from tasks arraylist.
     */
    public Task removeTask(int index) {
        return this.tasks.remove(index);
    }

    /**
     * Marks task as done from existing arraylist of tasks from specified index.
     *
     * @param index Index of task to be marked.
     * @return Task marked from tasks arraylist.
     */
    public Task markTask(int index) {
        Task task = tasks.get(index);
        task.markDone();
        return task;
    }

    /**
     * Unmarks task as done from existing arraylist of tasks from specified index.
     *
     * @param index Index of task to be unmarked.
     * @return Task unmarked from tasks arraylist.
     */
    public Task unmarkTask(int index) {
        Task task = tasks.get(index);
        task.unmarkDone();
        return task;
    }

    public Task updateTask(int index, String description) {
        Task task = tasks.get(index);
        task.updateDescription(description);
        return task;
    }

    /**
     * Returns size of the tasklist.
     *
     * @return int size of the tasklist.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Returns true if tasklist is empty, else returns false.
     *
     * @return boolean indicating if tasklist is empty.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns taskList of all tasks with matching description
     *
     * @param desc Description of the task to find
     * @return TaskList of all matching tasks
     */
    public TaskList findTasks(String desc) {
        TaskList taskList = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().contains(desc)) {
                taskList.addTask(task);
            }
        }
        return taskList;
    }

    /**
     * Returns all tasks in string format to save to file.
     *
     * @return String of tasks to be saved.
     */
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
            sb.append("\n ").append(i + 1).append(".").append(task.toString());
        }
        return sb.toString();
    }
}
