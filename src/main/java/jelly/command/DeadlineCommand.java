package jelly.command;

import java.time.LocalDate;

import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Deadline;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDate by;

    public DeadlineCommand(String description, LocalDate by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        Deadline deadline = taskList.addDeadline(description, by);
        storage.write(taskList);
        return ui.showAddTask(deadline, taskList.getSize());
    }
}
