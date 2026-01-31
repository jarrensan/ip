package jelly.command;

import jelly.Storage;
import jelly.exception.JellyException;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class MarkCommand extends Command{
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        Task task = taskList.markTask(index);
        storage.write(taskList);
        return ui.showMarkTask(task);
    }
}
