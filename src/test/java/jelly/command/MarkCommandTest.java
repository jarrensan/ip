package jelly.command;

import jelly.StorageTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.TaskTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class MarkCommandTest extends CommandTest {
    private final int index;

    public MarkCommandTest(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) throws JellyExceptionTest {
        TaskTest task = taskList.markTask(index);
        storage.write(taskList);
        return ui.showMarkTask(task);
    }
}
