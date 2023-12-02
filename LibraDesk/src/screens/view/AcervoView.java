package screens.view;

import java.util.List;

import javax.swing.JOptionPane;
import java.util.*;
import java.sql.*;
import conexaoDAO.Conexao;
import java.net.URL;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.swing.JOptionPane;

import DAO.AcervoDAO;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.LivroModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import DAO.AcervoDAO;
import controller.AcervoController;
import controller.EditarLivroController;
import controller.Main;
import controller.NovoLivroController;
import model.LivroModel;

public class AcervoView {

    AcervoController acervoController = new AcervoController();

    @FXML
    private TextField txtTituloPesquisado;

    public String getTextField() {
        return txtTituloPesquisado.getText();
    }

    @FXML
    protected void btNovoLivro(ActionEvent e) throws Exception {

        Main.changeScreen("novoLivro");
    }

    @FXML
    protected void btLeitores(ActionEvent e) throws Exception {
        Main.changeScreen("leitores");
    }

    @FXML
    protected void btEmprestimos(ActionEvent e) throws Exception {
        Main.changeScreen("emprestimos");
    }

    @FXML
    protected void btEmAtraso(ActionEvent e) throws Exception {
        Main.changeScreen("em_atraso");
    }

    @FXML
    protected void btFuncionario(ActionEvent e) throws Exception {
        Main.changeScreen("funcionario");
    }

    @FXML
    protected void btPerfil(ActionEvent e) throws Exception {
        Main.changeScreen("perfil");
    }

    @FXML
    private TableView<LivroModel> livrosTableView;

    @FXML
    public void initialize() {
        TableColumn<LivroModel, String> colTitulo = new TableColumn("Titulo");
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));

        TableColumn<LivroModel, String> colAutor = new TableColumn("Autor");
        colAutor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAutor()));

        TableColumn<LivroModel, String> colLocalizacao = new TableColumn("Localizacao");
        colLocalizacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocalBiblioteca()));

        TableColumn<LivroModel, Integer> colNumExemplares = new TableColumn("Numero Exemplares");
        colNumExemplares.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getNumeroExemplares()).asObject());

        TableColumn<LivroModel, Integer> colId = new TableColumn("Id");
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        livrosTableView.getColumns().addAll(colId, colTitulo, colAutor, colLocalizacao, colNumExemplares);

        atualizarTabela();
    }

    public void atualizarTabela() {
        List<LivroModel> acervo = acervoController.pegarLivrosAcervo();
        preencherTableView(acervo);
    }

    @FXML
    protected void btBuscarLivrosPorTitulo(ActionEvent event) {
        String titulo = txtTituloPesquisado.getText();
        List<LivroModel> livros = acervoController.buscarLivrosPorTitulo(titulo);
        preencherTableView(livros);
    }

    public void preencherTableView(List<LivroModel> livros) {
        ObservableList<LivroModel> livrosObservableList = FXCollections.observableArrayList(livros);
        livrosTableView.setItems(livrosObservableList);
    }

    @FXML
    public void btExcluirLivro(ActionEvent e) {
        LivroModel livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();
        openExcluirPopup(livroSelecionado.getId());
    }

    @FXML
    public void btEditarLivro(ActionEvent e) throws Exception {
        LivroModel livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();
        System.out.println("1" + livroSelecionado.getTitulo());
        if (livroSelecionado == null) {
            System.out.println(livroSelecionado.getTitulo());
            // Mostre uma mensagem para o usuário selecionar um livro antes de editar
            return;
        }
        EditarLivroController controller = EditarLivroController.getInstance();
        if (controller == null) {
            System.out.println("controler nulo");
            // Mostre uma mensagem para o usuário que a tela de edição não foi carregada
            // corretamente
            return;
        }
        
        
        
        controller.preencherCampos(livroSelecionado);
        System.out.println("4");
        Main.changeScreen("editarLivro");
    }

    private void openEditarLivro(LivroModel livro) {
        try {
            // Carregando o arquivo FXML da tela de edição
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../fxml/EditarLivro.fxml"));
            Parent root = loader.load();

            // Obtendo o controlador da tela de edição
            EditarLivroController controller = loader.getController();

            // Passando o LeitorModel selecionado para o controlador
            controller.preencherCampos(livro);

            // Criando um novo palco (Stage) para a tela de edição
            Stage edicaoLeitorStage = new Stage();
            edicaoLeitorStage.setTitle("Editar Livro");
            edicaoLeitorStage.initStyle(StageStyle.UTILITY);
            edicaoLeitorStage.initModality(Modality.APPLICATION_MODAL);
            edicaoLeitorStage.setScene(new Scene(root, 992, 614));

            // Exibindo o palco
            edicaoLeitorStage.showAndWait();
        } catch (Exception ex) {
            // Tratamento de exceção (substitua por um tratamento adequado)
            ex.printStackTrace();
        }
    }

    private void openExcluirPopup(int id) {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../fxml/ConfirmarExcluir.fxml"));
            Parent root = loader.load();

            ConfirmarExcluirLivroView confirmarExcluirLivroView = loader.getController();
            confirmarExcluirLivroView.setAcervoController(acervoController);
            confirmarExcluirLivroView.setIdLivro(id);
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
