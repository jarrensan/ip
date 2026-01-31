package jelly.command;

import jelly.Storage;
import jelly.exception.JellyException;
import jelly.task.TaskList;
import jelly.task.Todo;
import jelly.ui.Ui;

public class TodoCommand extends Command {

    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws JellyException {
        Todo todo = taskList.addTodo(description);
        storage.write(taskList);
        return ui.showAddTask(todo, taskList.getSize());
    }

}
