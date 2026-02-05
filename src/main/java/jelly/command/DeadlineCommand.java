package jelly.command;

import java.time.LocalDate;

import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Deadline;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDate byDateTime;

    public DeadlineCommand(String description, LocalDate byDateTime) {
        this.description = description;
        this.byDateTime = byDateTime;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        Deadline deadline = taskList.addDeadline(description, byDateTime);
        storage.write(taskList);
        return ui.showAddTask(deadline, taskList.getSize());
    }
}
