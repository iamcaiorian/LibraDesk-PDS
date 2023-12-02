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

import DAO.AcervoDAO;
import javafx.stage.Stage;
import javafx.scene.Node;

import model.LivroModel;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class NovoLivroController {

    AcervoDAO acervoDAO = new AcervoDAO();

    // A conexão com o banco de dados

    @FXML
    private TextField tituloLivro;
    @FXML
    private TextField autorLivro;
    @FXML
    private TextField localizacaoLivro;
    @FXML
    private TextField numExemplaresLivro;
    
    @FXML
    public void initialize(){
            tituloLivro.setText("AAA");
           }

    @FXML
    public void BtCadastrar(ActionEvent e) throws Exception  {
        // public LivroModel(String titulo, int id, String localBiblioteca, int
        // numeroExemplares, String autor)
        LivroModel livro = new LivroModel(tituloLivro.getText(), 0, localizacaoLivro.getText(),
                Integer.parseInt(numExemplaresLivro.getText()), autorLivro.getText());
        acervoDAO.adicionarLivro(livro);
        Main.changeScreen("acervo");

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    public void btCancelar(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
    }

}
