package jelly.command;

import jelly.StorageTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.TaskTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class UnmarkCommandTest extends CommandTest {
    private final int index;

    public UnmarkCommandTest(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) throws JellyExceptionTest {
        TaskTest task = taskList.unmarkTask(index);
        storage.write(taskList);
        return ui.showUnmarkTask(task);
    }
}
