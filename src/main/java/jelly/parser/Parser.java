package jelly.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.jdi.*;
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
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
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
        case FIND:
            return findCommandEvent(input);
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
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        int ind = parseIndex(inputString.get(1));
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
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));

        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        int ind = parseIndex(inputString.get(1));
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

    /**
     * Returns todoCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return todoCommand with saved data.
     * @throws InvalidArgumentException If unable to parse argument.
     */
    public TodoCommand todoCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() < 2) {
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

    /**
     * Returns DeleteCommand based on user's arguments.
     *
     * @param input Raw string data from user's input.
     * @return DeleteCommand with saved data.
     * @throws InvalidArgumentException If unable to parse argument.
     */
    public DeleteCommand deleteCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));

        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        int index = parseIndex(inputString.get(1));
        return new DeleteCommand(index);
    }

    public FindCommand findCommandEvent(String input) throws InvalidArgumentException {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));

        if (inputString.size() != 2) {
            throw new InvalidArgumentException();
        }
        String description = inputString.get(1);
        return new FindCommand(description);
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
