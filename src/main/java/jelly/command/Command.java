package jelly.command;

import jelly.Storage;
import jelly.task.TaskList;
import jelly.ui.Ui;
import jelly.exception.JellyException;

public abstract class Command {
    public abstract String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException;
}
