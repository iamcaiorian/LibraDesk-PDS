/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import javafx.collections.ObservableList;
import conexaoDAO.Conexao;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.LeitorModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javax.swing.JOptionPane;

import DAO.LeitoresDAO;

/**
 * FXML Controller class
 *
 * @author CAIO
 */
public class LeitoresController {

    LeitoresDAO leitoresDAO = new LeitoresDAO();

    @FXML
    private TextField txtLeitorPesquisado;

    @FXML
    private TableView<LeitorModel> leitoresTableView;

    @FXML
    protected void btAcervo(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
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
    protected void btNovoLeitor(ActionEvent e) throws Exception {
        openNovoLeitorPopup();
    }

    @FXML
    protected void btPerfil(ActionEvent e) throws Exception {
        Main.changeScreen("perfil");
    }

    @FXML
    protected void btFuncionario(ActionEvent e) throws Exception {
        Main.changeScreen("funcionario");
    }

    @FXML
    protected void btEditarLeitor(ActionEvent e) {
        LeitorModel leitorSelecionado = leitoresTableView.getSelectionModel().getSelectedItem();

        try {
            // Carregando o arquivo FXML da tela de edição
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/EditarLeitor.fxml"));
            Parent root = loader.load();

            // Obtendo o controlador da tela de edição
            EditarLeitorController controller = loader.getController();

            // Passando o LeitorModel selecionado para o controlador
            controller.preencherCampos(leitorSelecionado);

            // Criando um novo palco (Stage) para a tela de edição
            Stage edicaoLeitorStage = new Stage();
            edicaoLeitorStage.setTitle("Editar Leitor");
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

    private void openNovoLeitorPopup() {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/NovoLeitor.fxml"));
            Parent root = loader.load();

            NovoLeitorController controller = loader.getController();

            // Passando o LeitorModel selecionado para o controlador

            // Criando um novo palco (Stage) para a tela NovoLivro
            Stage novoLeitorStage = new Stage();
            novoLeitorStage.setTitle("Novo Leitor");
            novoLeitorStage.initStyle(StageStyle.UTILITY);
            novoLeitorStage.initModality(Modality.APPLICATION_MODAL);
            novoLeitorStage.setScene(new Scene(root, 992, 614));

            // Exibindo o palco
            novoLeitorStage.showAndWait();
        } catch (Exception e) {
            // Tratamento de exceção (substitua por um tratamento adequado)
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Coluna para Nome Completo usando getNomeCompleto
        TableColumn<LeitorModel, String> colNomeCompleto = new TableColumn<>("Nome Completo");
        colNomeCompleto.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getNomeCompleto()));

        // Coluna para CPF
        TableColumn<LeitorModel, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getCpf()));

        // Coluna para Telefone Um
        TableColumn<LeitorModel, String> colTelefoneUm = new TableColumn<>("Telefone Um");
        colTelefoneUm.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getTelefoneUm()));

        // Coluna para Telefone Dois
        TableColumn<LeitorModel, String> colTelefoneDois = new TableColumn<>("Telefone Dois");
        colTelefoneDois.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getTelefoneDois()));

        // Coluna para Número
        TableColumn<LeitorModel, Integer> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(data -> new SimpleIntegerProperty(
                data.getValue().getNumero()).asObject());

        // Coluna para Endereço usando getEndereco
        TableColumn<LeitorModel, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getEndereco()));

        // Adicionando as colunas à TableView
        leitoresTableView.getColumns().addAll(colNomeCompleto, colCpf, colTelefoneUm, colTelefoneDois, colEndereco,
                colNumero);

        // Obtendo a lista de leitores e preenchendo a TableView
        atualizarTabela();
    }

    public void atualizarTabela() {
        List<LeitorModel> leitores = leitoresDAO.pegarLeitores();
        preencherTableViewLeitor(leitores);
    }



    @FXML
    protected void btBuscarLeitorPorNome(ActionEvent event) {
        String nome = txtLeitorPesquisado.getText();
        List<LeitorModel> leitores = leitoresDAO.pesquisarLeitorPorNome(nome);
        preencherTableViewLeitor(leitores);
    }

    public void preencherTableViewLeitor(List<LeitorModel> leitores) {
        ObservableList<LeitorModel> leitoresObservableList = FXCollections.observableArrayList(leitores);
        leitoresTableView.setItems(leitoresObservableList);
    }

    @FXML
    public void btExcluirLeitor(ActionEvent e) {
        LeitorModel leitorSelecionado = leitoresTableView.getSelectionModel().getSelectedItem();

        leitoresDAO.excluirLeitor(leitorSelecionado.getCpf());
    }

}
