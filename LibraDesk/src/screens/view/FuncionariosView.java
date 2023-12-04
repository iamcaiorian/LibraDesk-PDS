package screens.view;

import java.util.List;

import controller.FuncionarioController;
import controller.Main;
import model.BibliotecariaModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.EmprestimoModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FuncionariosView {
    FuncionarioController  funcionarioController = new FuncionarioController();

    @FXML
    private TableView<BibliotecariaModel> TableViewFuncionario;

    @FXML
    protected void btVoltar(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
    }

     @FXML
    protected void btExcluir(ActionEvent e) throws Exception {
        openExcluirPopup();
    }
    
    @FXML
    protected void btPerfil(ActionEvent e)throws Exception {
        Main.changeScreen("perfil");
    }
    
    @FXML
    protected void btConfirmarEdicao(ActionEvent e)throws Exception {
        //openEditarPopup();
    }
    
    @FXML
    public void initialize(){
        TableColumn<BibliotecariaModel, String> colNome = new TableColumn<>("Funcionário");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomeCompleto()));

        TableColumn<BibliotecariaModel, String> colCargo = new TableColumn<>("Cargo");
        colCargo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().cargoCoordenador()));

        TableViewFuncionario.getColumns().addAll(colNome, colCargo);
        atualizarTabela();
    }

    public void atualizarTabela(){
        List<BibliotecariaModel> listaBibliotecaria = funcionarioController.getFuncionarios();
        preencherTableView(listaBibliotecaria);
    }

    

    public void preencherTableView(List<BibliotecariaModel> listaBibliotecaria){       
        ObservableList<BibliotecariaModel> observableListBibliotecaria = FXCollections.observableArrayList(listaBibliotecaria);
        TableViewFuncionario.setItems(observableListBibliotecaria);
    }

    private static void openExcluirPopup() {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/ConfirmarExcluir.fxml"));
            Parent root = loader.load();

            // Criando um novo palco (Stage) para a tela NovoLivro
            Stage excluirStage = new Stage();
            excluirStage.setTitle("Confrimar Exclusão");
            excluirStage.initStyle(StageStyle.UTILITY);
            excluirStage.initModality(Modality.APPLICATION_MODAL);
            excluirStage.setScene(new Scene(root, 530, 200));

            // Exibindo o palco
            excluirStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     
}
