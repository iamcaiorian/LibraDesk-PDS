package command;

import controller.IController;

public interface PopUpCommand {
    public IController execute() throws Exception;
}
