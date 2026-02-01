package jelly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jelly.command.Command;
import jelly.exception.JellyException;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class Jelly {
    private TaskList taskList;
    private final Ui ui;
    private final Parser parser;
    private final Storage storage;

    public Jelly(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage();

        try {
            storage.create(filePath);
            taskList = storage.load();
        } catch (JellyException e) {
            ui.showError(e);
            taskList = new TaskList();
        }
    }

    /**
     * Run program
     */
    public void run() throws IOException {
        printOutput(ui.showGreeting());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = br.readLine();
            try {
                Command command = parser.parse(input);
                String output = command.execute(taskList, ui, storage);
                printOutput(output);
                if (command.isExit()) {
                    break;
                }
            } catch (JellyException e) {
                printOutput(ui.showError(e));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Jelly("data/Jelly.txt").run();
    }

    /**
     * Prints out the line specified in the input param output.
     *
     * @param output String data to be outputted.
     */
    public void printOutput(String output) {
        System.out.println(output);
    }
}
