package jelly;

import javafx.application.Platform;
import jelly.command.Command;
import jelly.exception.JellyException;
import jelly.parser.Parser;
import jelly.storage.Storage;
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

    public String getResponse(String input) {
        try {
            Command command = parser.parse(input);
            assert command != null : "Command should not be empty";
            String response = command.execute(taskList, ui, storage);
            if (command.isExit()) {
                Platform.exit();
            }
            assert response != null && !response.isEmpty() : "Response should not be empty";
            return response;
        } catch (JellyException e) {
            return ui.showError(e);
        }
    }

    public String getGreeting() {
        return ui.showGreeting();
    }
}
