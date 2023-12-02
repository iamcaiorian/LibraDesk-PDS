/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

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

/**
 * FXML Controller class
 *
 * @author CAIO
 */
public class AcervoController {

    AcervoDAO acervoDAO = new AcervoDAO();

    @FXML
    private TextField txtTituloPesquisado;

    @FXML
    protected void btNovoLivro(ActionEvent e) throws Exception {
        openNovoLivroPopup();
    }

    @FXML
    protected void btLeitores(ActionEvent e) throws Exception  {
        Main.changeScreen("leitores");
    }

    @FXML
    protected void btEmprestimos(ActionEvent e) throws Exception {
        Main.changeScreen("emprestimos");
    }

    @FXML
    protected void btEmAtraso(ActionEvent e) throws Exception  {
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

    private void openNovoLivroPopup() {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/NovoLivro.fxml"));
            Parent root = loader.load();

            NovoLivroController controller = loader.getController();
            controller.setAcervoController(this);

            // Criando um novo palco (Stage) para a tela NovoLivro
            Stage novoLivroStage = new Stage();
            novoLivroStage.setTitle("Novo Livro");
            novoLivroStage.initStyle(StageStyle.UTILITY);
            novoLivroStage.initModality(Modality.APPLICATION_MODAL);
            novoLivroStage.setScene(new Scene(root, 992, 614));

            // Exibindo o palco
            novoLivroStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableView<LivroModel> livrosTableView;

    List<LivroModel> livros;

    @FXML
    protected void btBuscarLivrosPorTitulo(ActionEvent event) {

        String titulo = txtTituloPesquisado.getText(); // Suponha que você tenha um campo de texto para digitar o
                                                       // título.
        livros = acervoDAO.pesquisarLivroPorTitulo(titulo);
        preencherTableView(livros);
    }

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
        List<LivroModel> acervo = acervoDAO.pegarLivrosAcervo();
        preencherTableView(acervo);
    }



    public void preencherTableView(List<LivroModel> livros) {
        ObservableList<LivroModel> livrosObservableList = FXCollections.observableArrayList(livros);
        livrosTableView.setItems(livrosObservableList);
    }


    @FXML
    public void btOpenEditarLivro() {
        LivroModel livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();

        try {
            // Carregando o arquivo FXML da tela de edição
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/EditarLivro.fxml"));
            Parent root = loader.load();

            // Obtendo o controlador da tela de edição
            EditarLivroController controller = loader.getController();

            // Passando o LeitorModel selecionado para o controlador
            controller.preencherCampos(livroSelecionado);

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

    boolean confirmacao = false;

    public void setConfirmacao(boolean confirmacao) {
        this.confirmacao = confirmacao;
    }
  
    @FXML
    public void BtExcluirLivro(ActionEvent e) {
        LivroModel livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();
        Conexao conSing = Conexao.getInstancy();
        Connection conexao = conSing.getConexao();
        openExcluirPopup();
        if (confirmacao) {
            acervoDAO.excluirLivro(livroSelecionado.getId());
        }else{
            JOptionPane.showMessageDialog(null, "Exclusão cancelada!");
        }
    }

    private void openExcluirPopup() {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/ConfirmarExcluir.fxml"));
            Parent root = loader.load();

            ConfirmarExcluirController controller = loader.getController();
            controller.setAcervoController(this);
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
