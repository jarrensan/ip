package jelly;

import jelly.command.ByeCommand;
import jelly.command.Command;
import jelly.command.CommandList;
import jelly.command.DeadlineCommand;
import jelly.command.DeleteCommand;
import jelly.command.EventCommand;
import jelly.command.ListCommand;
import jelly.command.MarkCommand;
import jelly.command.TodoCommand;
import jelly.command.UnmarkCommand;

import jelly.exception.InvalidArgumentException;
import jelly.exception.InvalidCommandException;
import jelly.exception.InvalidDateException;
import jelly.exception.JellyException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
            return new ListCommand();
        case MARK:
            return markCommandEvent(input);
        case UNMARK:
            return unmarkCommandEvent(input);
        case TODO:
            return todoCommandEvent(input);
        case DEADLINE:
            return deadlineCommandEvent(input);
        case EVENT:
            return eventCommandEvent(input);
        case DELETE:
            return deleteCommandEvent(input);
        case BYE:
            return new ByeCommand();
        default:
            throw new InvalidCommandException();
        }
    }

    public Command markCommandEvent(String input) throws JellyException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        int ind = Integer.parseInt(inputString.get(1)) - 1;
        return new MarkCommand(ind);
    }

    public Command unmarkCommandEvent(String input) throws JellyException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }

        int ind = Integer.parseInt(inputString.get(1)) - 1;
        return new UnmarkCommand(ind);
    }

    public Command eventCommandEvent(String input) throws JellyException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
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
            return new EventCommand(description, from, to);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Correct format: event <description> /from <dd/MMM/yyyy> /to <dd/MMM/yyyy>");
        }
    }

    public Command todoCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() < 2) {
            throw new InvalidArgumentException();
        }
        String description = input.substring(4).trim();
        return new TodoCommand(description);
    }

    public Command deadlineCommandEvent(String input) throws JellyException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        int byInd = input.indexOf("/by");
        if (inputString.size() < 4 || byInd == -1) {
            throw new InvalidArgumentException();
        }
        try {
            String description = input.substring(8, byInd).trim();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate by = LocalDate.parse(input.substring(byInd + 3).trim(), format);
            return new DeadlineCommand(description, by);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Correct format: deadline <description> /by <dd/MMM/yyyy>");
        }
    }

    public Command deleteCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        int index = Integer.parseInt(inputString.get(1)) - 1;
        return new DeleteCommand(index);
    }
}
