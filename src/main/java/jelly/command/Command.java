package jelly.command;

import jelly.exception.JellyException;
import jelly.storage.Storage;
import jelly.task.TaskList;
import jelly.ui.Ui;

public abstract class Command {
    protected boolean isExit = false;

    /**
     * Returns response from command execution to be shown to user.
     *
     * @param taskList Task list of all existing tasks.
     * @param ui User interface for displaying messages.
     * @param storage Storage for reading from or writing to data file.
     * @return String of the response.
     * @throws JellyException If command encounters execution errors.
     */
    public abstract String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException;

    /**
     * Returns if isExit is set to true form other Command subclasses.
     * By default, isExit is set to false.
     *
     * @return Boolean of whether program should exit.
     */
    public boolean isExit() {
        return isExit;
    }
}
