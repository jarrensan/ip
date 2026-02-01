package jelly;

import static org.junit.jupiter.api.Assertions.*;

import jelly.command.*;
import jelly.parser.Parser;
import org.junit.jupiter.api.*;

public class ParserTest {
    @Test
    public void parse_markCommand_success() {
        Parser parse = new Parser();
        try {
            assertInstanceOf(MarkCommand.class, parse.parse("mark 20"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_unmarkCommand_success() {
        Parser parse = new Parser();
        try {
            assertInstanceOf(UnmarkCommand.class, parse.parse("unmark 10"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_todoCommand_success() {
        Parser parse = new Parser();
        try {
            assertInstanceOf(TodoCommand.class, parse.parse("todo testing"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_deadlineCommand_success() {
        Parser parse = new Parser();
        try {
            assertInstanceOf(DeadlineCommand.class, parse.parse("deadline testing /by 01/Jan/2026"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_deadlineCommand_exceptionThrown() {
        Parser parse = new Parser();
        try {
            parse.parse("deadline testing /by 2026/Jan/01");
            fail();
        } catch (Exception e) {
            assertEquals("Uh-oh.... Correct format: deadline <description> /by <dd/MMM/yyyy>", e.getMessage());
        }
    }

    @Test
    public void parse_eventCommand_success() {
        Parser parse = new Parser();
        try {
            assertInstanceOf(EventCommand.class, parse.parse("event testing /from 01/Jan/2026 /to 30/Dec/2027"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_eventCommand_exceptionThrown() {
        Parser parse = new Parser();
        try {
            parse.parse("event testing /from 01/Mar/2109 /to 02/Testing/2002");
            fail();
        } catch (Exception e) {
            assertEquals("Uh-oh.... Correct format: event <description> /from <dd/MMM/yyyy> /to <dd/MMM/yyyy>",
                    e.getMessage());
        }
    }

    @Test
    public void parse_deleteCommand_success() {
        Parser parse = new Parser();
        try {
            assertInstanceOf(DeleteCommand.class, parse.parse("delete 3"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_byeCommand_success() {
        Parser parse = new Parser();
        try {
            assertInstanceOf(ByeCommand.class, parse.parse("bye"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_wrongCommand_exceptionThrown() {
        Parser parse = new Parser();
        try {
            parse.parse("Listing");
            fail();
        } catch (Exception e) {
            assertEquals("Uh-oh.... Invalid Command! Please try again!", e.getMessage());
        }
    }
}
