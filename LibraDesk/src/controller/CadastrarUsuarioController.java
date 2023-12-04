/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import DAO.FuncionarioDAO;
import conexaoDAO.Conexao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.PessoaModel;
import model.BibliotecariaModel;

/**
 * FXML Controller class
 *
 * @author CAIO
 */
public class CadastrarUsuarioController implements IController {

    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void CadastrarUsuario(String primeiroNome, String sobrenome, String cpf, String email, String senha,
            boolean coordenador) {

        funcionarioDAO.cadastrarPessoa(primeiroNome, sobrenome, cpf);
        funcionarioDAO.CadastrarBibliotecaria(email, senha, cpf);

    }

    private void openConfirmarPopup() {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/ConfirmarCadastro.fxml"));
            Parent root = loader.load();

            ConfirmarCadastroController controller = loader.getController();
            controller.setCadastrarUsuarioController(this);
            // Criando um novo palco (Stage) para a tela NovoLivro
            Stage confirmarStage = new Stage();
            confirmarStage.setTitle("Confirmar Cadastro");
            confirmarStage.initStyle(StageStyle.UTILITY);
            confirmarStage.initModality(Modality.APPLICATION_MODAL);
            confirmarStage.setScene(new Scene(root, 480, 360));

            // Exibindo o palco
            confirmarStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
