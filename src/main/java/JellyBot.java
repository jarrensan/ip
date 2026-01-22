import exception.InvalidArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class JellyBot {
    private static final ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printOutput(Message.greeting());
        while (true) {
            String input = br.readLine();
            if (input.equals("bye")) break;
            runCommand(input);
            input = br.readLine();
        }
        printOutput(Message.bye());
    }

    public static void runCommand(String input) {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        String output = "";
        Command command;
        try {
            command = Command.valueOf(inputString.get(0));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Command! Please try again!");
            return;
        }
        switch (command) {
            case LIST:
                output = Message.printList(taskList);
                break;
            case MARK:
                try {
                    markTask(inputString);
                } catch (Exception e) {
                    output = e.getMessage();
                }
                break;
            case UNMARK:
                try {
                    output = unmarkTask(inputString);
                } catch (Exception e) {
                    output = e.getMessage();
                }
                break;
            case TODO:
                try {
                    addTodoTask(inputString);
                } catch (InvalidArgumentException e) {
                    output = e.getMessage();
                }
                break;
            case DEADLINE:
                try {
                    addDeadlineTask(inputString);
                } catch (InvalidArgumentException e) {
                    output = e.getMessage();
                }
                break;
            case EVENT:
                try {
                    addEventTask(inputString);
                } catch (InvalidArgumentException e) {
                    output = e.getMessage();
                }
                break;
        }
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

    public static void addEventTask(ArrayList<String> inputString) throws InvalidArgumentException {

    }

    public static void addDeadlineTask(ArrayList<String> inputString) throws InvalidArgumentException {

    }

    public static void addTodoTask(ArrayList<String> inputString) throws InvalidArgumentException {

    }

    public static void printOutput(String output) {
        System.out.println(output);
    }


}
