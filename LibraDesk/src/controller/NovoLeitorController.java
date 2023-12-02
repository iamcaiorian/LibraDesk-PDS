/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import conexaoDAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

import DAO.LeitoresDAO;
import javafx.stage.Stage;
import javafx.scene.Node;

import model.LeitorModel;
import model.PessoaModel;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class NovoLeitorController {

    LeitoresDAO leitoresDAO = new LeitoresDAO();

    @FXML
    private TextField nomeLeitor;
    @FXML
    private TextField cpfLeitor;
    @FXML
    private TextField telefone1Leitor;
    @FXML
    private TextField telefone2Leitor;
    @FXML
    private TextField cidadeLeitor;
    @FXML
    private TextField bairroLeitor;
    @FXML
    private TextField ruaLeitor;
    @FXML
    private TextField numeroLeitor;

    
    @FXML
    public void btCadastrarLeitor(ActionEvent e) throws Exception  {
        String nomeCompleto = nomeLeitor.getText();
        String[] partesNome = nomeCompleto.split(" ", 2);
        String primeiroNome = partesNome[0];
        String sobrenome = (partesNome.length > 1) ? partesNome[1] : "";

        PessoaModel pessoa = new PessoaModel(primeiroNome, sobrenome, cpfLeitor.getText());
        leitoresDAO.adicionarPessoa(pessoa);

        LeitorModel leitor = new LeitorModel(
                primeiroNome,
                sobrenome,
                cpfLeitor.getText(),
                telefone1Leitor.getText(),
                telefone2Leitor.getText(),
                bairroLeitor.getText(),
                ruaLeitor.getText(),
                cidadeLeitor.getText(),
                Integer.parseInt(numeroLeitor.getText()));

        leitoresDAO.adicionarLeitor(leitor);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
        Main.changeScreen("leitores");
    }

}
