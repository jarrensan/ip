package jelly.command;

import jelly.Storage;
import jelly.exception.JellyException;
import jelly.task.TaskList;
import jelly.ui.Ui;

public abstract class Command {
    protected boolean isExit = false;

    public abstract String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException;
    public boolean isExit() {
        return isExit;
    }
}
