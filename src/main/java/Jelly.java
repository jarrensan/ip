import exception.InvalidArgumentException;
import exception.InvalidCommandException;
import exception.JellyException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Jelly {
    private static ArrayList<Task> taskList;
    private static Storage st;

    public static void main(String[] args) throws IOException {
        st = new Storage();
        try {
            st.createFile("data/Jelly.txt");
            taskList = st.loadTasks();
        } catch (JellyException e) {
            printOutput(Message.errorMessage(e));
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printOutput(Message.greeting());
        while (true) {
            String input = br.readLine();
            if (input.equals("bye")) break;
            try {
                runCommand(input);
            } catch (JellyException e) {
                printOutput(Message.errorMessage(e));
            }
        }
        printOutput(Message.bye());
    }

    public static void runCommand(String input) throws JellyException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        String output = "";
        Command command;
        try {
            command = Command.valueOf(inputString.get(0).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
        switch (command) {
        case LIST:
            output = Message.printList(taskList);
            break;
        case MARK:
            try {
                output = markTask(inputString);
            } catch (JellyException e) {
                output = Message.errorMessage(e);
            }
            break;
        case UNMARK:
            try {
                output = unmarkTask(inputString);
            } catch (JellyException e) {
                output = Message.errorMessage(e);
            }
            break;
        case TODO:
            try {
                output = addTodoTask(inputString, input);
            } catch (InvalidArgumentException e) {
                output = Message.errorMessage(e);
            }
            break;
        case DEADLINE:
            try {
                output = addDeadlineTask(inputString, input);
            } catch (InvalidArgumentException e) {
                output = Message.errorMessage(e);
            }
            break;
        case EVENT:
            try {
                output = addEventTask(inputString, input);
            } catch (InvalidArgumentException e) {
                output = Message.errorMessage(e);
            }
            break;
        case DELETE:
            try {
                output = deleteTask(inputString);
            } catch (InvalidArgumentException e) {
                output = Message.errorMessage(e);
            }
        }
        st.writeFile(taskList);
        printOutput(output);
    }

    public static String markTask(ArrayList<String> inputString) throws InvalidArgumentException {
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }

        int ind = Integer.parseInt(inputString.get(1)) - 1;
        Task task = taskList.get(ind);
        task.markDone();
        return Message.taskMarked(task);
    }

    public static String unmarkTask(ArrayList<String> inputString) throws InvalidArgumentException {
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }

        int ind = Integer.parseInt(inputString.get(1)) - 1;
        Task task = taskList.get(ind);
        task.unmarkDone();
        return Message.taskUnmarked(task);
    }

    public static void addTask(Task task) {
        taskList.add(task);
    }

    public static String addEventTask(ArrayList<String> inputString, String input) throws InvalidArgumentException {
        int fromInd = input.indexOf("/from");
        int toInd = input.indexOf("/to");
        if (inputString.size() < 6 || fromInd == -1 || toInd == -1 || fromInd >= toInd) {
            throw new InvalidArgumentException();
        }
        String description = input.substring(5, fromInd).trim();
        String from = input.substring(fromInd + 5, toInd).trim();
        String to = input.substring(toInd + 3).trim();
        Event event = new Event(description, from, to);
        addTask(event);
        return Message.addTask(event, taskList.size());
    }

    public static String addDeadlineTask(ArrayList<String> inputString, String input) throws InvalidArgumentException {
        int byInd = input.indexOf("/by");
        if (inputString.size() < 4 || byInd == -1) {
            throw new InvalidArgumentException();
        }
        String description = input.substring(8, byInd).trim();
        String by = input.substring(byInd + 3).trim();
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
        return Message.addTask(deadline, taskList.size());
    }

    public static String addTodoTask(ArrayList<String> inputString, String input) throws InvalidArgumentException {
        if (inputString.size() < 2) {
            throw new InvalidArgumentException();
        }
        String description = input.substring(4).trim();
        Todo todo = new Todo(description);
        addTask(todo);
        return Message.addTask(todo, taskList.size());
    }

    public static void printOutput(String output) {
        System.out.println(output);
    }

    public static String deleteTask(ArrayList<String> inputString) throws InvalidArgumentException{
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        int ind = Integer.parseInt(inputString.get(1)) - 1;
        Task task = taskList.remove(ind);
        return Message.deleteTask(task, taskList.size());
    }


}
