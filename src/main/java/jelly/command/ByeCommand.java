package jelly.command;
import jelly.Storage;
import jelly.task.TaskList;
import jelly.ui.Ui;

public class ByeCommand extends Command{

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        this.isExit = true;
        return ui.showBye();
    }
}
