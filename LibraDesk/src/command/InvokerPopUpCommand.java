package command;

import java.util.HashMap;
import java.util.Map;
import controller.IController;

public class InvokerPopUpCommand {
    private Map<String, PopUpCommand> commandMap = new HashMap<>();

    public void register(String commandName, PopUpCommand command) {
        commandMap.put(commandName, command);
    }

    public IController invoke(String commandName) throws Exception {
        PopUpCommand command = commandMap.get(commandName);
        if (command != null) {
            return command.execute();
        }
        return null;
    }
}
