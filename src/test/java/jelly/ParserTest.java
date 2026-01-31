package jelly;

import jelly.command.CommandTest;
import jelly.command.CommandListTest;
import jelly.command.ListCommandTest;
import jelly.command.MarkCommandTest;
import jelly.command.ByeCommandTestTest;
import jelly.command.UnmarkCommandTest;
import jelly.command.EventCommandTest;
import jelly.command.TodoCommandTest;
import jelly.command.DeadlineCommandTest;
import jelly.command.DeleteCommandTest;

import jelly.exception.InvalidArgumentExceptionTest;
import jelly.exception.InvalidCommandExceptionTest;
import jelly.exception.InvalidDateExceptionTest;
import jelly.exception.JellyExceptionTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class ParserTest {
    public CommandTest parse(String input) throws JellyExceptionTest {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        String output = "";
        CommandListTest command;
        try {
            command = CommandListTest.valueOf(inputString.get(0).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandExceptionTest();
        }
        switch (command) {
        case LIST:
            return new ListCommandTest();
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
            return new ByeCommandTestTest();
        default:
            throw new JellyExceptionTest("Error! Command not found!");
        }
    }

    public CommandTest markCommandEvent(String input) throws JellyExceptionTest {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() != 2) {
            throw new InvalidArgumentExceptionTest();
        }
        int ind = Integer.parseInt(inputString.get(1)) - 1;
        return new MarkCommandTest(ind);
    }

    public CommandTest unmarkCommandEvent(String input) throws JellyExceptionTest {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() != 2) {
            throw new InvalidArgumentExceptionTest();
        }

        int ind = Integer.parseInt(inputString.get(1)) - 1;
        return new UnmarkCommandTest(ind);
    }

    public CommandTest eventCommandEvent(String input) throws JellyExceptionTest {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        int fromInd = input.indexOf("/from");
        int toInd = input.indexOf("/to");
        if (inputString.size() < 6 || fromInd == -1 || toInd == -1 || fromInd >= toInd) {
            throw new InvalidArgumentExceptionTest();
        }
        try {
            String description = input.substring(5, fromInd).trim();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate from = LocalDate.parse(input.substring(fromInd + 5, toInd).trim(), format);
            LocalDate to = LocalDate.parse(input.substring(toInd + 3).trim(), format);
            return new EventCommandTest(description, from, to);
        } catch (DateTimeParseException e) {
            throw new InvalidDateExceptionTest("Correct format: event <description> /from <dd/MMM/yyyy> /to <dd/MMM/yyyy>");
        }
    }

    public CommandTest todoCommandEvent(String input) throws InvalidArgumentExceptionTest {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() < 2) {
            throw new InvalidArgumentExceptionTest();
        }
        String description = input.substring(4).trim();
        return new TodoCommandTest(description);
    }

    public CommandTest deadlineCommandEvent(String input) throws JellyExceptionTest {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        int byInd = input.indexOf("/by");
        if (inputString.size() < 4 || byInd == -1) {
            throw new InvalidArgumentExceptionTest();
        }
        try {
            String description = input.substring(8, byInd).trim();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate by = LocalDate.parse(input.substring(byInd + 3).trim(), format);
            return new DeadlineCommandTest(description, by);
        } catch (DateTimeParseException e) {
            throw new InvalidDateExceptionTest("Correct format: deadline <description> /by <dd/MMM/yyyy>");
        }
    }

    public CommandTest deleteCommandEvent(String input) throws InvalidArgumentExceptionTest {
        ArrayList<String> inputString = new ArrayList<>(Arrays.asList(input.split(" ")));
        if (inputString.size() != 2) {
            throw new InvalidArgumentExceptionTest();
        }
        int index = Integer.parseInt(inputString.get(1)) - 1;
        return new DeleteCommandTest(index);
    }
}
