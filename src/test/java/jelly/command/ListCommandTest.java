package jelly.command;

import jelly.StorageTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class ListCommandTest extends CommandTest {
    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) {
        return ui.showList(taskList);
    }
}
