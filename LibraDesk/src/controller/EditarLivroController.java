/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

import DAO.AcervoDAO;
import model.LivroModel;

/**
 *
 * @author gabri
 */
public class EditarLivroController {

    AcervoDAO acervoDAO = new AcervoDAO();
    
    @FXML
    private TextField tituloLivro;
    @FXML
    private TextField autorLivro;
    @FXML
    private TextField localizacaoLivro;
    @FXML
    private TextField numExemplaresLivro;
    
    private int idLivro;
    
    
    public void preencherCampos(LivroModel livro){
        tituloLivro.setText(livro.getTitulo());
        autorLivro.setText(livro.getAutor());
        localizacaoLivro.setText(livro.getLocalBiblioteca());    
        numExemplaresLivro.setText(String.valueOf(livro.getNumeroExemplares()));
        idLivro = livro.getId();
    }
    
    @FXML
    public void btEditarLivro(ActionEvent e) throws Exception {
        LivroModel livro = new LivroModel(tituloLivro.getText(), 
                idLivro, 
                localizacaoLivro.getText(), 
                Integer.parseInt(numExemplaresLivro.getText()), 
                autorLivro.getText());
        
        EditarLivro(livro);
        
        Main.changeScreen("acervo");
    }
    
    @FXML
    public void btCancelar(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
    }
    
    public void EditarLivro(LivroModel livro){
        acervoDAO.EditarLivro(livro);
    
    }
}
