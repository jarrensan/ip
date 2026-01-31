package jelly;

import jelly.command.Command;
import jelly.command.CommandList;
import jelly.exception.InvalidArgumentException;
import jelly.exception.InvalidCommandException;
import jelly.exception.JellyException;
import jelly.ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    public Command parse(String input) throws JellyException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        String output = "";
        CommandList command;
        try {
            command = CommandList.valueOf(inputString.get(0).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
        switch (command) {
            case LIST:
                output = Ui.showPrintList(taskList);
                break;
            case MARK:
                try {
                    output = markTask(inputString);
                } catch (JellyException e) {
                    output = Ui.showError(e);
                }
                break;
            case UNMARK:
                try {
                    output = unmarkTask(inputString);
                } catch (JellyException e) {
                    output = Ui.showError(e);
                }
                break;
            case TODO:
                try {
                    output = addTodoTask(inputString, input);
                } catch (InvalidArgumentException e) {
                    output = Ui.showError(e);
                }
                break;
            case DEADLINE:
                try {
                    output = addDeadlineTask(inputString, input);
                } catch (InvalidArgumentException e) {
                    output = Ui.showError(e);
                }
                break;
            case EVENT:
                try {
                    output = addEventTask(inputString, input);
                } catch (InvalidArgumentException e) {
                    output = Ui.showError(e);
                }
                break;
            case DELETE:
                try {
                    output = deleteTask(inputString);
                } catch (InvalidArgumentException e) {
                    output = Ui.showError(e);
                }
        }
        st.writeFile(taskList);
        printOutput(output);
    }

}
