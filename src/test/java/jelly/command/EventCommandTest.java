package jelly.command;

import java.time.LocalDate;
import jelly.StorageTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.EventTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class EventCommandTest extends CommandTest {
    private final String description;
    private final LocalDate from;
    private final LocalDate to;

    public EventCommandTest(String description, LocalDate from, LocalDate to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) throws JellyExceptionTest {
        EventTest event = taskList.addEvent(description, from, to);
        storage.write(taskList);
        return ui.showAddTask(event, taskList.getSize());
    }
}
