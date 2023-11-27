/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

import conexaoDataBase.Conexao;
import model.LivroModel;

/**
 *
 * @author gabri
 */
public class EditarLivroController {
    
    @FXML
    private TextField tituloLivro;
    @FXML
    private TextField autorLivro;
    @FXML
    private TextField localizacaoLivro;
    @FXML
    private TextField numExemplaresLivro;
    
    private int idLivro;
    
    private AcervoController acervoController;
    
    public void setAcervoController(AcervoController acervoController){
        this.acervoController = acervoController;
    }
    
    public void preencherCampos(Livro livro){
        tituloLivro.setText(livro.getTitulo());
        autorLivro.setText(livro.getAutor());
        localizacaoLivro.setText(livro.getLocalBiblioteca());    
        numExemplaresLivro.setText(String.valueOf(livro.getNumeroExemplares()));
        idLivro = livro.getId();
    }
    
    @FXML
    public void btEditarLivro(ActionEvent e){
        Livro livro = new Livro(tituloLivro.getText(), 
                idLivro, 
                localizacaoLivro.getText(), 
                Integer.parseInt(numExemplaresLivro.getText()), 
                autorLivro.getText());
        
        EditarLivro(livro);
        
        acervoController.atualizarTabela();
        Main.changeScreen("acervo");
    }
    
    @FXML
    public void btCancelar(ActionEvent e){
        Main.changeScreen("acervo");
    }
    
    public void EditarLivro(Livro livro){
           
        try{
            Conexao conSing = Conexao.getInstancy();
            Connection conexao = conSing.getConexao();
            String sql = "UPDATE Livro SET titulo = ?, local_biblioteca = ?, num_exemplares = ?, autor = ? WHERE id = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, livro.getTitulo());
            preparedStatement.setString(2, livro.getLocalBiblioteca());
            preparedStatement.setInt(3, livro.getNumeroExemplares());
            preparedStatement.setString(4, livro.getAutor());    
            preparedStatement.setInt(5, livro.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + ex.getMessage());
        }
    
    }
}
