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
import screens.view.NovoFuncionarioView;

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
        funcionarioDAO.CadastrarBibliotecaria(email, senha, cpf, coordenador);

    }

    
}
