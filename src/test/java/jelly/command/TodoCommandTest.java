package jelly.command;

import jelly.StorageTest;
import jelly.exception.JellyExceptionTest;
import jelly.task.TaskListTest;
import jelly.task.TodoTest;
import jelly.ui.UiTest;

public class TodoCommandTest extends CommandTest {

    private final String description;

    public TodoCommandTest(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskListTest taskList, UiTest ui, StorageTest storage) throws JellyExceptionTest {
        TodoTest todo = taskList.addTodo(description);
        storage.write(taskList);
        return ui.showAddTask(todo, taskList.getSize());
    }

}
