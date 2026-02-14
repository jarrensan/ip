package jelly.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import jelly.command.*;
import jelly.exception.InvalidArgumentException;
import jelly.exception.InvalidCommandException;
import jelly.exception.InvalidDateException;
import jelly.exception.JellyException;


public class Parser {
    /**
     * Returns subclass of Command based on user's input.
     *
     * @param input Raw string data from user's input.
     * @return subclass of Command.
     * @throws JellyException If unable to parse command.
     */
    public Command parse(String input) throws JellyException {
        ArrayList<String> inputStrings = new ArrayList<>(Arrays.asList(input.split(" ")));
        CommandList command;
        try {
            command = CommandList.valueOf(inputStrings.get(0).toUpperCase());
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
        case FIND:
            return findCommandEvent(input);
        case UPDATE:
            return updateCommandEvent(input);
        case BYE:
            return new ByeCommand();
        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Returns MarkCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return MarkCommand with saved data.
     * @throws JellyException If unable to parse command.
     */
    public MarkCommand markCommandEvent(String input) throws JellyException {
        ArrayList<String> inputStrings = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputStrings.size() != 2) {
            throw new InvalidArgumentException();
        }
        int ind = parseIndex(inputStrings.get(1));
        return new MarkCommand(ind);
    }

    /**
     * Returns UnmarkCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return UnmarkCommand with saved data.
     * @throws JellyException If unable to parse command.
     */
    public UnmarkCommand unmarkCommandEvent(String input) throws JellyException {
        ArrayList<String> inputArgs = new ArrayList<>(Arrays.asList(input.split(" ")));

        if (inputArgs.size() != 2) {
            throw new InvalidArgumentException();
        }
        int ind = parseIndex(inputArgs.get(1));
        return new UnmarkCommand(ind);
    }

    /**
     * Returns EventCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return EventCommand with saved data.
     * @throws JellyException If unable to parse command.
     */
    public EventCommand eventCommandEvent(String input) throws JellyException {
        ArrayList<String> inputArgs = new ArrayList<>(Arrays.asList(input.split(" ")));
        int fromTaskIndex = input.indexOf("/from");
        int toTaskIndex = input.indexOf("/to");

        if (inputArgs.size() < 6 || fromTaskIndex == -1 || toTaskIndex == -1 || fromTaskIndex >= toTaskIndex) {
            throw new InvalidArgumentException();
        }

        try {
            String description = input.substring(5, fromTaskIndex).trim();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate fromDateTime = LocalDate.parse(input.substring(fromTaskIndex + 5, toTaskIndex).trim(), format);
            LocalDate toDateTime = LocalDate.parse(input.substring(toTaskIndex + 3).trim(), format);
            return new EventCommand(description, fromDateTime, toDateTime);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Correct format: event <description> /from <dd/MMM/yyyy> /to <dd/MMM/yyyy>");
        }
    }

    /**
     * Returns todoCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return todoCommand with saved data.
     * @throws InvalidArgumentException If unable to parse argument.
     */
    public TodoCommand todoCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputArgs = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputArgs.size() < 2) {
            throw new InvalidArgumentException();
        }
        String description = input.substring(4).trim();
        return new TodoCommand(description);
    }

    /**
     * Returns DeadlineCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return DeadlineCommand with saved data.
     * @throws JellyException If unable to parse command.
     */
    public DeadlineCommand deadlineCommandEvent(String input) throws JellyException {
        ArrayList<String> inputArgs = new ArrayList<>(Arrays.asList(input.split(" ")));
        int byInd = input.indexOf("/by");

        if (inputArgs.size() < 4 || byInd == -1) {
            throw new InvalidArgumentException();
        }
        try {
            String description = input.substring(8, byInd).trim();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate byDateTime = LocalDate.parse(input.substring(byInd + 3).trim(), format);
            return new DeadlineCommand(description, byDateTime);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Correct format: deadline <description> /by <dd/MMM/yyyy>");
        }
    }

    /**
     * Returns DeleteCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return DeleteCommand with saved data.
     * @throws InvalidArgumentException If unable to parse argument.
     */
    public DeleteCommand deleteCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputArgs = new ArrayList<>(Arrays.asList(input.split(" ")));

        if (inputArgs.size() != 2) {
            throw new InvalidArgumentException();
        }
        int index = parseIndex(inputArgs.get(1));
        return new DeleteCommand(index);
    }

    /**
     * Returns FindCommand based on user's input.
     *
     * @param input Raw string data from user's input.
     * @return FindCommand with saved data.
     * @throws InvalidArgumentException If unable to parse argument.
     */
    public FindCommand findCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputArgs = new ArrayList<>(Arrays.asList(input.split(" ")));

        if (inputArgs.size() != 2) {
            throw new InvalidArgumentException();
        }
        String description = inputArgs.get(1);
        return new FindCommand(description);
    }

    public UpdateCommand updateCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputArgs = new ArrayList<>(Arrays.asList(input.split(" ", 3)));

        if (inputArgs.size() < 3) {
            throw new InvalidArgumentException();
        }
        int taskIndex = parseIndex(inputArgs.get(1));
        String description = inputArgs.get(2);
        return new UpdateCommand(taskIndex, description);
    }

    /**
     * Returns int based on user's arguments of the task index.
     *
     * @param s Raw string data from user's argument of the task index.
     * @return int of the task index.
     * @throws InvalidArgumentException If unable to parse index from string to int.
     */
    private int parseIndex(String s) throws InvalidArgumentException {
        try {
            return Integer.parseInt(s) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException();
        }
    }
}
