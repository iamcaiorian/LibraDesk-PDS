package controller;

import conexaoDAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.w3c.dom.Node;

import DAO.LoginDAO;

public class ConfirmarCadastroController implements IController {

    LoginDAO loginDAO = new LoginDAO();

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;

    public CadastrarUsuarioController cadastrarUsuarioController;

    public void setCadastrarUsuarioController(CadastrarUsuarioController cadastrarUsuarioController) {
        this.cadastrarUsuarioController = cadastrarUsuarioController;
    }

    @FXML
    protected void btConfirmar(ActionEvent e) {
        String login = txtLogin.getText();
        String senha = txtSenha.getText();

        if (loginDAO.validarCampos(login, senha)) {
            cadastrarUsuarioController.CadastrarUsuario();
            
        } else {
            JOptionPane.showMessageDialog(null, "Login ou senha incorretos");
        }
    }

    @FXML
    protected void btCancelar(ActionEvent e) throws Exception {
        Main.changeScreen("novoUsuario");
    }

    
}
