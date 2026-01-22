import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JellyBot {
    private static final String h_line = "\n____________________________________________________________\n";
    private static final ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String greeting = " Hello! I'm JellyBot\n What can I do for you?";
        String bye = " Bye. Hope to see you again soon!";

        System.out.println(h_line + greeting + h_line);

        String input = br.readLine();
        while (!input.equals("bye")) {
            runCommand(input);
            input = br.readLine();
        }
        System.out.println(h_line + bye + h_line);
    }

    public static void runCommand(String input) {
        String output = "";

        if (input.equals("list")) {
            output = printList();
        } else if (input.startsWith("mark")) {
            output = markTask(input);
        } else if (input.startsWith("unmark")) {
            output = unmarkTask(input);
        } else {
            addTask(input);
            output = input;
        }
        System.out.println(h_line + output + h_line);
    }

    public static String printList() {
        String list_out = "Here are the tasks in your list: ";
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String isDone = " ";
            list_out += ("\n" + (i + 1) + "." + printTask(task));
        }
        return list_out;
    }

    public static String markTask(String input) {
        String[] is = input.split(" ");
        int ind = Integer.parseInt(is[1]) - 1;
        Task task = taskList.get(ind);
        task.markDone();
        String output = "Nice! I've marked this task as done: ";
        output += "\n" + printTask(task);
        return output;
    }

    public static String unmarkTask(String input) {
        String[] is = input.split(" ");
        int ind = Integer.parseInt(is[1]) - 1;
        Task task = taskList.get(ind);
        task.unmarkDone();
        String output = "OK, I've marked this task as not done yet: ";
        output += "\n" + printTask(task);
        return output;
    }

    public static void addTask(String input) {
        Task task = new Task(input);
        taskList.add(task);
    }

    public static String printTask(Task task) {
        return "[" + task.getStatusIcon() + "] " + task.getDescription();
    }
}
