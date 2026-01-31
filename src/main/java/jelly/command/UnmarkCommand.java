package jelly.command;

import jelly.Storage;
import jelly.exception.JellyException;
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
        Task task = taskList.unmarkTask(index);
        storage.write(taskList);
        return ui.showUnmarkTask(task);
    }
}
