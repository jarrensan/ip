package jelly.command;

import jelly.storage.Storage;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class FindCommand extends Command {
    private final String description;

    public FindCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        TaskList newTaskList = taskList.findTasks(description);
        return ui.showFindList(newTaskList);
    }
}
