package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import conexaoDAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

import DAO.LeitoresDAO;
import model.LeitorModel;
import model.PessoaModel;
/**
 *
 * @author gabri
 */
public class EditarLeitorController {

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
     
    public void preencherCampos(LeitorModel leitor){
        nomeLeitor.setText(leitor.getNomeCompleto());
        cpfLeitor.setText(leitor.getCpf());
        telefone1Leitor.setText(leitor.getTelefoneUm());
        telefone2Leitor.setText(leitor.getTelefoneDois());
        ruaLeitor.setText(leitor.getRua());
        cidadeLeitor.setText(leitor.getCidade());
        bairroLeitor.setText(leitor.getBairro());
        numeroLeitor.setText(String.valueOf(leitor.getNumero()));
    }
    
    @FXML
    public void btEditarLeitor(ActionEvent e) throws Exception {
        String nomeCompleto = nomeLeitor.getText();
        String[] partesNome = nomeCompleto.split(" ", 2);
        String primeiroNome = partesNome[0];
        String sobrenome = (partesNome.length > 1) ? partesNome[1] : "";
        
        
        PessoaModel pessoa = new PessoaModel(primeiroNome, sobrenome, cpfLeitor.getText());
        editarPessoa(pessoa);
        
        LeitorModel leitor = new LeitorModel(
                primeiroNome,
                sobrenome,
                cpfLeitor.getText(),
                telefone1Leitor.getText(),
                telefone2Leitor.getText(),
                bairroLeitor.getText(),
                ruaLeitor.getText(),
                cidadeLeitor.getText(),
                Integer.parseInt(numeroLeitor.getText())
        );
        
        editarLeitor(leitor);
        Main.changeScreen("leitores");
        
    }
    
    public void editarPessoa(PessoaModel pessoa){
        leitoresDAO.editarPessoa(pessoa);
    }
    
    public void editarLeitor(LeitorModel leitor) throws Exception {
        leitoresDAO.editarLeitor(leitor);
    }
    
    
    @FXML
    public void btCancelarLeitor(ActionEvent e) throws Exception {
        Main.changeScreen("leitores");
    }
}
