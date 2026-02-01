package jelly.command;

import jelly.Storage;
import jelly.exception.InvalidTaskNumberException;
import jelly.exception.JellyException;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class DeleteCommand extends Command {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        if (this.index < 0 || this.index >= taskList.getSize()) {
            throw new InvalidTaskNumberException(this.index + 1);
        }

        Task task = taskList.removeTask(index);
        storage.write(taskList);
        return ui.showDeleteTask(task, taskList.getSize());
    }


}
