package jelly.command;
import jelly.StorageTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class ByeCommandTestTest extends CommandTest {
    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) {
        this.isExit = true;
        return ui.showBye();
    }
}
