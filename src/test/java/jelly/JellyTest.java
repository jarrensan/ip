package jelly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jelly.command.CommandTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class JellyTest {
    private StorageTest storage;
    private TaskListTest taskList;
    private final UiTest ui;
    private final ParserTest parser;

    public JellyTest(String filePath) {
        ui = new UiTest();
        parser = new ParserTest();

        try {
            storage = new StorageTest(filePath);
            taskList = new TaskListTest(storage.load());
        } catch (JellyExceptionTest e) {
            ui.showError(e);
            taskList = new TaskListTest();
        }
    }

    public void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printOutput(ui.showGreeting());

        while (true) {
            String input = br.readLine();
            try {
                CommandTest command = parser.parse(input);
                String output = command.execute(taskList, ui, storage);
                printOutput(output);
                if (command.isExit()) {
                    break;
                }
            } catch (JellyExceptionTest e) {
                printOutput(ui.showError(e));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new JellyTest("data/Jelly.txt").run();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }
}
