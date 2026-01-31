package jelly.command;

import jelly.StorageTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.TaskTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class DeleteCommandTest extends CommandTest {

    private final int index;

    public DeleteCommandTest(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) throws JellyExceptionTest {
        TaskTest task = taskList.removeTask(index);
        storage.write(taskList);
        return ui.showDeleteTask(task, taskList.getSize());
    }


}
