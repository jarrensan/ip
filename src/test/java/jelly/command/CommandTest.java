package jelly.command;

import jelly.StorageTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public abstract class CommandTest {
    protected boolean isExit = false;

    public abstract String execute(TaskListTest taskList, UiTest ui, StorageTest storage) throws JellyExceptionTest;
    public boolean isExit() {
        return isExit;
    }
}
