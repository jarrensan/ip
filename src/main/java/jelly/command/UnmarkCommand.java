package jelly.command;

import jelly.exception.InvalidTaskNumberException;
import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        if (this.index < 0 || this.index >= taskList.getSize()) {
            throw new InvalidTaskNumberException(this.index + 1);
        }

        Task task = taskList.unmarkTask(index);
        storage.write(taskList);
        return ui.showUnmarkTask(task);
    }
}
