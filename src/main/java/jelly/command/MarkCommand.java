package jelly.command;

import jelly.exception.InvalidTaskNumberException;
import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        if (this.index < 0 || this.index >= taskList.getSize()) {
            throw new InvalidTaskNumberException(this.index + 1);
        }

        Task task = taskList.markTask(index);
        storage.write(taskList);
        return ui.showMarkTask(task);
    }
}
