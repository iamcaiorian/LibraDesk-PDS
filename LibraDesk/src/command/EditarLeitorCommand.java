package command;

import controller.EditarLeitorController;
import controller.Main;
import screens.view.AcervoView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

public class EditarLeitorCommand implements PopUpCommand {
    
    public void execute(LeitorModel leitor) throws Exception {
        
        // Parent xmlEditarLivro = FXMLLoader.load(getClass().getResource("../screens/fxml/EditarLivro.fxml"));
        // // Criando um novo palco (Stage) para a tela editarLivro
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../screens/fxml/EditarLivro.fxml"));
        Parent xmlEditarLivro = fxmlLoader.load();
        EditarLeitorController controller = fxmlLoader.getController();
        controller.preencherCampos(leitor);

        Scene editarLivro = new Scene(xmlEditarLivro, 992, 614);
        editarLivro.getStylesheets().add(getClass().getResource("../screens/css/style.css").toExternalForm());
        Stage editarLivroStage = new Stage();
        editarLivroStage.setTitle("Novo Livro");
        editarLivroStage.initStyle(StageStyle.UTILITY);
        editarLivroStage.initModality(Modality.APPLICATION_MODAL);
        editarLivroStage.setScene(editarLivro);
        editarLivroStage.showAndWait();
    }
}
