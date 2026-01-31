package jelly.command;

public class ByeCommand extends Command{

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.showBye();
    }
}
