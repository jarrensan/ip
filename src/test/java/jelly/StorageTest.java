package jelly;

import static org.junit.jupiter.api.Assertions.*;

import jelly.exception.CreateFileException;
import jelly.exception.JellyException;
import jelly.exception.LoadFileException;
import jelly.storage.Storage;
import jelly.task.Deadline;
import jelly.task.Event;
import jelly.task.Todo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.*;

import java.io.*;
import java.nio.file.*;

public class StorageTest {
    @TempDir
    Path tempDir;

    @Test
    public void create_invalidPath_exceptionThrown() {
        Storage storage = new Storage();
        Exception e = assertThrows(CreateFileException.class, () -> {
            storage.create("??.txt");
        });

        assertEquals("Uh-oh.... Error creating file!", e.getMessage());
    }

    @Test
    public void load_missingFile_exceptionThrown() {
        Storage storage = new Storage();

        assertThrows(LoadFileException.class, () -> {
            storage.create("test.txt");
            File f = new File("test.txt");
            f.delete();
            storage.load();
        });
    }

    @Test
    public void getTask_validTodoString_success() throws JellyException {
        Storage storage = new Storage();
        String input = "T | 0 | read book";

        assertInstanceOf(Todo.class, storage.getTask(input));
    }

    @Test
    public void getTask_validDeadlineString_success() throws JellyException {
        Storage storage = new Storage();
        String input = "D | 0 | submit assignment | by: 2025-Oct-15";

        assertInstanceOf(Deadline.class, storage.getTask(input));
    }

    @Test
    public void getTask_validEventString_success() throws JellyException {
        Storage storage = new Storage();
        String input = "E | 0 | finish homework | from: 2018-Jan-02 | to: 2022-Feb-04";

        assertInstanceOf(Event.class, storage.getTask(input));
    }
}
