package command;

import controller.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class EmAtrasoCommand implements Command{
    public void execute() throws Exception{
        Parent xmlEmAtraso = FXMLLoader.load(getClass().getResource("../view/Em_Atraso.fxml"));
        Scene EmAtraso = new Scene(xmlEmAtraso, 1280, 720);
        EmAtraso.getStylesheets().add(getClass().getResource("../libradesk/style.css").toExternalForm());
        Main.changeScene(EmAtraso); 
    }
}
