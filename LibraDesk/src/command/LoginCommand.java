package command;

import controller.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginCommand implements Command{
    public void execute() throws Exception{
        Parent xmlLogin = FXMLLoader.load(getClass().getResource("../view/Entrar.fxml"));
        Scene Login = new Scene(xmlLogin, 1280, 720);
        Login.getStylesheets().add(getClass().getResource("../libradesk/styleEntrar.css").toExternalForm());
        Main.changeScene(Login);
    }
}
