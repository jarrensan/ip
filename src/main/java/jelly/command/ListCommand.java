package jelly.command;

import jelly.storage.Storage;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class ListCommand extends Command {
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.showList(taskList);
    }
}
