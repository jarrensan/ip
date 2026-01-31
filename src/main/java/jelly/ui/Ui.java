package jelly.ui;

import jelly.task.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ui {

    BufferedReader br;

    public Ui() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
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

    public String showAddTask(Task task, int task_size) {
        return h_line +
                " Got it. I've added this task:\n   " +
                task +
                "\n Now you have " + task_size + " in the list." +
                h_line;
    }

    public String showError(Exception e) {
        return h_line + e.getMessage() + h_line;
    }

    public String showPrintList(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) return h_line + " Empty List! Add some tasks first!" + h_line;

        StringBuilder sb = new StringBuilder(" Here are the tasks in your list: ");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String isDone = " ";
            sb.append("\n " + (i + 1) + "." + task.toString());
        }
        return h_line + sb.toString() + h_line;
    }

    public String showDeleteTask(Task task, int task_size) {
        return h_line +
                "Noted. I've removed this task:\n   " +
                task +
                "\n Now you have " + task_size + " in the list." +
                h_line;
    }

}
