package jelly.ui;

import jelly.task.Task;
import jelly.task.TaskList;

public class Ui {
    private static final String h_line = "\n____________________________________________________________\n";

    public String showGreeting() {
        return h_line + " Hello! I'm JellyBot\n What can I do for you?" + h_line;
    }

    public String showBye() {
        return h_line + " Bye. Hope to see you again soon!" + h_line;
    }

    public String showMarkTask(Task task) {
        return h_line + "Nice! I've marked this task as done: \n" + task + h_line;
    }

    public String showUnmarkTask(Task task) {
        return h_line + "OK, I've marked this task as not done yet: \n" + task + h_line;
    }

    public String showAddTask(Task task, int taskSize) {
        return h_line
                + " Got it. I've added this task:\n   " + task
                + "\n Now you have " + taskSize + " in the list." + h_line;
    }

    public String showError(Exception e) {
        return h_line + e.getMessage() + h_line;
    }

    public String showList(TaskList taskList) {
        if (taskList.isEmpty()) {
            return h_line + " Empty List! Add some tasks first!" + h_line;
        }
        return h_line + " Here are the tasks in your list: " + taskList + h_line;
    }

    public String showFindList(TaskList taskList) {
        if (taskList.isEmpty()) {
            return h_line + " No matching tasks found! Please try again." + h_line;
        }
        return h_line + " Here are the matching tasks in your list: " + taskList + h_line;
    }

    public String showUpdateTask(Task task) {
        return h_line + "OK, I've updated this task to: \n" + task + h_line;
    }

    public String showDeleteTask(Task task, int taskSize) {
        return h_line
                + "Noted. I've removed this task:\n   " + task
                + "\n Now you have " + taskSize + " in the list." + h_line;
    }
}
