package command;

import controller.EditarLivroController;
import controller.IController;
import controller.Main;
import screens.view.AcervoView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.LivroModel;

public class EditarLivroCommand implements PopUpCommand {
    
    public void execute(LivroModel livro) throws Exception {
        
        // Parent xmlEditarLivro = FXMLLoader.load(getClass().getResource("../screens/fxml/EditarLivro.fxml"));
        // // Criando um novo palco (Stage) para a tela editarLivro
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../screens/fxml/EditarLivro.fxml"));
        Parent xmlEditarLivro = fxmlLoader.load();
        EditarLivroController controller = fxmlLoader.getController();
        controller.preencherCampos(livro);

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
