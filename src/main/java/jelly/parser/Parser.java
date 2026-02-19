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
    private static final String DATE_FORMAT = "dd/MMM/yyyy";
    private static final String PREFIX_BY = "/by";
    private static final String PREFIX_FROM = "/from";
    private static final String PREFIX_TO = "/to";

    /**
     * Returns subclass of Command based on user's input.
     *
     * @param input Raw string data from user's input.
     * @return subclass of Command.
     * @throws JellyException If unable to parse command.
     */
    public Command parse(String input) throws JellyException {
        ArrayList<String> inputStrings = tokenize(input);
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
        ArrayList<String> inputStrings = tokenize(input);
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
        ArrayList<String> inputArgs = tokenize(input);

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
        ArrayList<String> inputArgs = tokenize(input);
        int fromTaskIndex = input.indexOf(PREFIX_FROM);
        int toTaskIndex = input.indexOf(PREFIX_TO);

        if (inputArgs.size() < 6 || fromTaskIndex == -1 || toTaskIndex == -1 || fromTaskIndex >= toTaskIndex) {
            throw new InvalidArgumentException();
        }

        try {
            String description = input.substring(inputArgs.get(0).length(), fromTaskIndex).trim();
            LocalDate fromDateTime = parseDate(input.substring(fromTaskIndex + PREFIX_FROM.length(), toTaskIndex).trim(),
                    "Correct format: event <description> /from <dd/MMM/yyyy> /to <dd/MMM/yyyy>");
            LocalDate toDateTime = parseDate(input.substring(toTaskIndex + PREFIX_TO.length()).trim(),
                    "Correct format: event <description> /from <dd/MMM/yyyy> /to <dd/MMM/yyyy>");
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
        ArrayList<String> inputArgs = tokenize(input);
        if (inputArgs.size() < 2) {
            throw new InvalidArgumentException();
        }
        String description = input.substring(inputArgs.get(0).length()).trim();
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
        ArrayList<String> inputArgs = tokenize(input);
        int byInd = input.indexOf(PREFIX_BY);

        if (inputArgs.size() < 4 || byInd == -1) {
            throw new InvalidArgumentException();
        }
        try {
            String description = input.substring(inputArgs.get(0).length(), byInd).trim();
            LocalDate byDateTime = parseDate(input.substring(byInd + PREFIX_BY.length()).trim(),
                    "Correct format: deadline <description> /by <dd/MMM/yyyy>");
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
        ArrayList<String> inputArgs = tokenize(input);

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
        ArrayList<String> inputArgs = tokenize(input);

        if (inputArgs.size() != 2) {
            throw new InvalidArgumentException();
        }
        String description = inputArgs.get(1);
        return new FindCommand(description);
    }

    /**
     * Returns UpdateCommand based on user's input.
     *
     * @param input Raw string data from user's input.
     * @return UpdateCommand with saved data.
     * @throws InvalidArgumentException If unable to parse argument.
     */
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


    // Used Microsoft Copilot to improve code cleaniless and added tokenize method to parser
    /**
     * Tokenizes the input string into individual words.
     *
     * @param input Raw string data from user's input.
     * @return ArrayList of tokenized strings.
     * @throws InvalidArgumentException If input is null or empty.
     */
    private ArrayList<String> tokenize(String input) throws InvalidArgumentException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidArgumentException();
        }
        return new ArrayList<>(Arrays.asList(input.trim().split("\\s+")));
    }

    /**
     * Parses a date string in the specified format into a LocalDate object.
     *
     * @param input Raw string data representing a date.
     * @param errorMessage Error message to display if parsing fails.
     * @return LocalDate parsed from the input string.
     * @throws InvalidDateException If unable to parse the date string.
     */
    private LocalDate parseDate(String input, String errorMessage) throws InvalidDateException {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return LocalDate.parse(input, format);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException(errorMessage);
        }
    }
}
