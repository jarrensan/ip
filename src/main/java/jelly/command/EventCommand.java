package jelly.command;

import java.time.LocalDate;

import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Event;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class EventCommand extends Command {
    private final String description;
    private final LocalDate from;
    private final LocalDate to;

    public EventCommand(String description, LocalDate from, LocalDate to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        Event event = taskList.addEvent(description, from, to);
        storage.write(taskList);
        return ui.showAddTask(event, taskList.getSize());
    }
}
