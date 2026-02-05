package jelly.command;

import jelly.exception.InvalidTaskNumberException;
import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int index) {
        this.taskIndex = index;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        if (this.taskIndex < 0 || this.taskIndex >= taskList.getSize()) {
            throw new InvalidTaskNumberException(this.taskIndex + 1);
        }

        Task task = taskList.unmarkTask(taskIndex);
        storage.write(taskList);
        return ui.showUnmarkTask(task);
    }
}
