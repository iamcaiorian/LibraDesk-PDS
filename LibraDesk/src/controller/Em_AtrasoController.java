/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import conexaoDAO.Conexao;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.swing.JOptionPane;

import DAO.EmAtrasoDAO;
import model.EmprestimoModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author CAIO
 */
public class Em_AtrasoController implements IController {

    EmAtrasoDAO emAtrasoDAO = new EmAtrasoDAO();

    // @FXML
    // protected void btLeitores(ActionEvent e) throws Exception {
    //     Main.changeScreen("leitores");
    // }

    // @FXML
    // protected void btEmprestimos(ActionEvent e) throws Exception {
    //     Main.changeScreen("emprestimos");
    // }

    // @FXML
    // protected void btAcervo(ActionEvent e) throws Exception {
    //     Main.changeScreen("acervo");
    // }

    // @FXML
    // protected void btPerfil(ActionEvent e) throws Exception {
    //     Main.changeScreen("perfil");
    // }

    // @FXML
    // protected void btFuncionario(ActionEvent e) throws Exception {
    //     Main.changeScreen("funcionario");
    // }
    
    // @FXML
    // private TableView<EmprestimoModel> emAtrasoTableView;
    
    // @FXML
    // private MenuButton btOpcaoBusca;
    
    // @FXML
    // private TextField txtCampoPesquisado;

    // @FXML
    // public void initialize() {

    //     MenuItem item1 = new MenuItem("Por leitor");
    //     MenuItem item2 = new MenuItem("Por titulo");

    //     item1.setOnAction(event -> handleOpcaoSelecionada(item1));
    //     item2.setOnAction(event -> handleOpcaoSelecionada(item2));

    //     btOpcaoBusca.getItems().addAll(item1, item2);

    //     TableColumn<EmprestimoModel, Integer> colIdEmprestimo = new TableColumn<>("Nº");
    //     colIdEmprestimo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdEmprestimo()).asObject());

    //     TableColumn<EmprestimoModel, String> colNomeLeitor = new TableColumn<>("Nome Leitor");
    //     colNomeLeitor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomeLeitor()));

    //     TableColumn<EmprestimoModel, String> colCpfLeitor = new TableColumn<>("CPF Leitor");
    //     colCpfLeitor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCpfLeitor()));

    //     TableColumn<EmprestimoModel, String> colNomeLivro = new TableColumn<>("Nome Livro");
    //     colNomeLivro.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomeLivro()));

    //     TableColumn<EmprestimoModel, Date> colDataEmprestimo = new TableColumn<>("Data Empréstimo");
    //     colDataEmprestimo.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDataEmprestimo()));

    //     TableColumn<EmprestimoModel, Date> colDataPrevDevolucao = new TableColumn<>("Data Devolução Prevista");
    //     colDataPrevDevolucao.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDataPrevDev()));

    //     TableColumn<EmprestimoModel, Double> colMulta = new TableColumn<>("Multa");
    //     colMulta.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMulta()));

    //     TableColumn<EmprestimoModel, String> colStatus = new TableColumn<>("Status");
    //     colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

    //     emAtrasoTableView.getColumns().addAll(colIdEmprestimo, colNomeLeitor, colCpfLeitor, colNomeLivro, colDataEmprestimo, colDataPrevDevolucao, colMulta, colStatus);
    //     atualizarTabela();

    // }

    public List<EmprestimoModel> pegarEmprestimos() {
        return emAtrasoDAO.pegarEmprestimos();
    }

    // private String getOpcaoBusca() {
    //     return btOpcaoBusca.getText();
    // }

    
    public List<EmprestimoModel> buscarEmprestimo(String opcaoBusca, String campoPesquisado) {

        if (opcaoBusca == "Por leitor") {
            List<EmprestimoModel> emprestimos = emAtrasoDAO.buscarEmprestimoPorLeitor(campoPesquisado);
            return emprestimos;
        } else if (opcaoBusca == "Por titulo") {
            List<EmprestimoModel> emprestimos = emAtrasoDAO.buscarEmprestimoPorLivro(campoPesquisado);
            return emprestimos;
        } else {
            JOptionPane.showMessageDialog(null, "selecione uma forma de pesquisa desejado");
        }

    }

    // public void preencherTableViewEmprestimo(List<EmprestimoModel> emprestimos) {
    //     ObservableList<EmprestimoModel> emprestimosObservableList = FXCollections.observableArrayList(emprestimos);
    //     emAtrasoTableView.setItems(emprestimosObservableList);
    // }

    // private void handleOpcaoSelecionada(MenuItem menuItem) {
    //     // Atualiza o texto do MenuButton com o texto do item selecionado
    //     btOpcaoBusca.setText(menuItem.getText());

    // }

    public void debitarEmprestimo(int idEmprestimo) {
        emAtrasoDAO.debitarEmprestimo(idEmprestimo);
    }

}
