package jelly;

import jelly.exception.InvalidArgumentException;
import jelly.exception.InvalidDateException;
import jelly.exception.JellyException;
import jelly.task.*;
import jelly.ui.Ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Jelly {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    public Jelly(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            storage = new Storage(filePath);
            taskList = new TaskList(storage.load());
        } catch (JellyException e) {
            ui.showError(e);
            taskList = new TaskList();
        }
    }

    public void run() {
//        try {
//            st.createFile("data/jelly.Jelly.txt");
//            taskList = st.loadTasks();
//        } catch (JellyException e) {
//            printOutput(jelly.ui.Ui.showError(e));
//        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printOutput(ui.showGreeting());
        while (true) {
            String input = br.readLine();
            if (input.equals("bye")) break;
            try {
                runCommand(input);
            } catch (JellyException e) {
                printOutput(Ui.showError(e));
            }
        }
        printOutput(Ui.bye());
    }

    public static String markTask(ArrayList<String> inputString) throws InvalidArgumentException {
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }

        int ind = Integer.parseInt(inputString.get(1)) - 1;
        Task task = taskList.get(ind);
        task.markDone();
        return Ui.showMarkTask(task);
    }

    public static String unmarkTask(ArrayList<String> inputString) throws InvalidArgumentException {
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }

        int ind = Integer.parseInt(inputString.get(1)) - 1;
        Task task = taskList.get(ind);
        task.unmarkDone();
        return Ui.showUnmarkTask(task);
    }

    public static void addTask(Task task) {
        taskList.add(task);
    }

    public String addEventTask(ArrayList<String> inputString, String input) throws JellyException {
        int fromInd = input.indexOf("/from");
        int toInd = input.indexOf("/to");
        if (inputString.size() < 6 || fromInd == -1 || toInd == -1 || fromInd >= toInd) {
            throw new InvalidArgumentException();
        }
        try {
            String description = input.substring(5, fromInd).trim();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate from = LocalDate.parse(input.substring(fromInd + 5, toInd).trim(), format);
            LocalDate to = LocalDate.parse(input.substring(toInd + 3).trim(), format);
            Event event = new Event(description, from, to);
            addTask(event);
            return Ui.showAddTask(event, taskList.size());
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Correct format: event <description> /from <dd/MMM/yyyy> /to <dd/MMM/yyyy>");
        }
    }

    public String addDeadlineTask(ArrayList<String> inputString, String input) throws JellyException {
        int byInd = input.indexOf("/by");
        if (inputString.size() < 4 || byInd == -1) {
            throw new InvalidArgumentException();
        }
        try {
            String description = input.substring(8, byInd).trim();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate by = LocalDate.parse(input.substring(byInd + 3).trim(), format);
            Deadline deadline = new Deadline(description, by);
            addTask(deadline);
            return Ui.showAddTask(deadline, taskList.size());
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Correct format: deadline <description> /by <dd/MMM/yyyy>");
        }
    }

    public String addTodoTask(ArrayList<String> inputString, String input) throws InvalidArgumentException {
        if (inputString.size() < 2) {
            throw new InvalidArgumentException();
        }
        String description = input.substring(4).trim();
        Todo todo = new Todo(description);
        addTask(todo);
        return ui.showAddTask(todo, taskList.size());
    }

    public void printOutput(String output) {
        System.out.println(output);
    }

    public String deleteTask(ArrayList<String> inputString) throws InvalidArgumentException{
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        int ind = Integer.parseInt(inputString.get(1)) - 1;
        Task task = taskList.remove(ind);
        return ui.showDeleteTask(task, taskList.size());

    }

    public static void main(String[] args) throws IOException {
        new Jelly("data/jelly.Jelly.txt").run();
    }


}
