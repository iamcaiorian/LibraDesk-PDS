/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import DAO.FuncionarioDAO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import conexaoDAO.Conexao;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BibliotecariaModel;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
/**
 * FXML Controller class
 *
 * @author arauj
 */
public class FuncionarioController{

    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    
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
        openEditarPopup();
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
        List<BibliotecariaModel> listaBibliotecaria = funcionarioDAO.getFuncionarios();
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


     private static void openEditarPopup() {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/EditarFuncionario.fxml"));
            Parent root = loader.load();

            // Criando um novo palco (Stage) para a tela NovoLivro
            Stage editarFuncionarioStage = new Stage();
            editarFuncionarioStage.setTitle("Editar Funcionario");
            editarFuncionarioStage.initStyle(StageStyle.UTILITY);
            editarFuncionarioStage.initModality(Modality.APPLICATION_MODAL);
            editarFuncionarioStage.setScene(new Scene(root, 992, 614));

            // Exibindo o palco
            editarFuncionarioStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    
    
}
