package jelly.ui;

import jelly.task.Task;
import jelly.task.TaskList;

public class Ui {
    private static final String h_line = "\n____________________________________________________________\n";

    public String showGreeting() {
        return h_line + " Blob! I'm Jelly blob!\n What can I do for you blob?" + h_line;
    }

    public String showBye() {
        return h_line + " Bleoob. Hope to see you again soon!" + h_line;
    }

    public String showMarkTask(Task task) {
        return h_line + "Bloob bloob! I've marked this task as done: \n" + task + h_line;
    }

    public String showUnmarkTask(Task task) {
        return h_line + "Blob..., I've marked this task as not done yet: \n" + task + h_line;
    }

    public String showAddTask(Task task, int taskSize) {
        return h_line
                + " Blob. I've added this task:\n   " + task
                + "\n Now you have " + taskSize + " task in the list blob." + h_line;
    }

    public String showError(Exception e) {
        return h_line + e.getMessage() + h_line;
    }

    public String showList(TaskList taskList) {
        if (taskList.isEmpty()) {
            return h_line + " Bubu... Empty List! Add some tasks first!" + h_line;
        }
        return h_line + " Blob! Here are the tasks in your list: " + taskList + h_line;
    }

    public String showFindList(TaskList taskList) {
        if (taskList.isEmpty()) {
            return h_line + " Blu... No matching tasks found! Please try again." + h_line;
        }
        return h_line + " Blim! Here are the matching tasks in your list: " + taskList + h_line;
    }

    public String showUpdateTask(Task task) {
        return h_line + "Blob, I've updated this task to: \n" + task + h_line;
    }

    public String showDeleteTask(Task task, int taskSize) {
        return h_line
                + "Blob blob. I've removed this task:\n   " + task
                + "\n Now you have " + taskSize + " in the list." + h_line;
    }
}
