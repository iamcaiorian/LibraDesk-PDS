package command;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import controller.Main;


public class EmprestimosCommand implements Command{
    public void execute() throws Exception{
        Parent xmlEmprestimos = FXMLLoader.load(getClass().getResource("../view/Emprestimos.fxml"));
        Scene Emprestimos = new Scene(xmlEmprestimos, 1280, 720);
        Emprestimos.getStylesheets().add(getClass().getResource("../libradesk/style.css").toExternalForm());
        Main.changeScene(Emprestimos);
    }
}
