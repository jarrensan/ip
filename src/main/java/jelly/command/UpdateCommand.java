package jelly.command;

import jelly.exception.InvalidTaskNumberException;
import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class UpdateCommand extends Command{
    private final int taskIndex;
    private final String description;

    public UpdateCommand(int taskIndex, String description) {
        this.taskIndex = taskIndex;
        this.description = description;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        if (this.taskIndex < 0 || this.taskIndex >= taskList.getSize()) {
            throw new InvalidTaskNumberException(this.taskIndex + 1);
        }
        Task task = taskList.updateTask(taskIndex, description);
        storage.write(taskList);
        return ui.showUpdateTask(task);
    }
}
