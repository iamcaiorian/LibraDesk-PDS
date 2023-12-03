/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import conexaoDAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import DAO.AcervoDAO;
import model.LivroModel;
import screens.view.AcervoView;
import model.IModel;

/**
 *
 * @author gabri
 */
public class EditarLivroController implements IController {

    public EditarLivroController controller;

    public EditarLivroController() {
        controller = this;
    }

    @FXML
    private TextField tituloLivro;
    @FXML
    private TextField autorLivro;
    @FXML
    private TextField localizacaoLivro;
    @FXML
    private TextField numExemplaresLivro;
    
    private int idLivro;
    
    AcervoDAO acervoDAO = new AcervoDAO();
    LivroModel livroModel;
    
    public void preencherCampos(LivroModel livro) {

        livroModel = livro;
        String titulo = livro.getTitulo();
        String autor = livro.getAutor();
        String localizacao = String.valueOf(livro.getLocalBiblioteca());
        String numExemplares = String.valueOf(livro.getNumeroExemplares());

        System.out.println("titulo: " + titulo);
        System.out.println("autor: " + autor);
        System.out.println("localizacao: " + localizacao);
        System.out.println("numExemplares: " + numExemplares);
        
        tituloLivro.setText(livro.getTitulo());
        autorLivro.setText(livro.getAutor());
        localizacaoLivro.setText(String.valueOf(livro.getLocalBiblioteca()));
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
    public void initialize(LivroModel livro) {
        if(livro != null) preencherCampos(livro);
    }

    @FXML
    public void btCancelar(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
    }

    public void EditarLivro(LivroModel livro) {
        acervoDAO.EditarLivro(livro);
    }
}
