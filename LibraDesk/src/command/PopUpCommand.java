package command;

import controller.IController;
import model.LivroModel;
import controller.EditarLivroController;

public interface PopUpCommand {
    public void execute(LivroModel livro) throws Exception;
}
