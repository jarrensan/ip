package jelly.command;

import java.time.LocalDate;

import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Event;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class EventCommand extends Command {
    private final String description;
    private final LocalDate fromDateTime;
    private final LocalDate toDateTime;

    public EventCommand(String description, LocalDate fromDateTime, LocalDate toDateTime) {
        this.description = description;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        Event event = taskList.addEvent(description, fromDateTime, toDateTime);
        storage.write(taskList);
        return ui.showAddTask(event, taskList.getSize());
    }
}
