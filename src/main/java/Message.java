import java.util.ArrayList;

public class Message {
    private static final String h_line = "\n____________________________________________________________\n";

    public static String greeting() {
        return h_line + " Hello! I'm JellyBot\n What can I do for you?" + h_line;
    }

    public static String bye() {
        return h_line + " Bye. Hope to see you again soon!" + h_line;
    }

    public static String taskMarked(Task task) {
        return h_line + "Nice! I've marked this task as done: \n" + task + h_line;
    }

    public static String taskUnmarked(Task task) {
        return h_line + "OK, I've marked this task as not done yet: \n" + task + h_line;
    }

    public static String addTask(Task task, int task_size) {
        return h_line +
                " Got it. I've added this task:\n" +
                task +
                "Now you have " + task_size + " in the list." +
                h_line;
    }

    public static String printList(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) return "Empty List! Add some tasks first!";

        StringBuilder sb = new StringBuilder("Here are the tasks in your list: ");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String isDone = " ";
            sb.append("\n" + (i + 1) + "." + task.toString());
        }
        return sb.toString();
    }

}
