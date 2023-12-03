package command;

import java.util.HashMap;
import java.util.Map;
import controller.IController;
import model.LivroModel;

public class InvokerPopUpCommand {
    private Map<String, PopUpCommand> commandMap = new HashMap<>();

    public void register(String commandName, PopUpCommand command) {
        commandMap.put(commandName, command);
    }

    public void invoke(String commandName, LivroModel livro) throws Exception {
        PopUpCommand command = commandMap.get(commandName);
        if (command != null) {
            command.execute(livro);
        }
    }
}
