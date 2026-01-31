package jelly.command;

import java.time.LocalDate;

import jelly.StorageTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.DeadlineTest;
import jelly.task.TaskListTest;
import jelly.ui.UiTest;

public class DeadlineCommandTest extends CommandTest {
    private final String description;
    private final LocalDate by;

    public DeadlineCommandTest(String description, LocalDate by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) throws JellyExceptionTest {
        DeadlineTest deadline = taskList.addDeadline(description, by);
        storage.write(taskList);
        return ui.showAddTask(deadline, taskList.getSize());
    }
}
