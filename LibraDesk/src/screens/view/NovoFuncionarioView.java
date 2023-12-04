package screens.view;

import command.*;
import controller.Main;
import model.BibliotecariaModel;
import model.PessoaModel;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.stage.Stage;

public class NovoFuncionarioView {
    InvokerPopUpCommand invokerPopUpCommand = new InvokerPopUpCommand();

    @FXML
    private TextField txtNomeUsuario;

    @FXML
    private TextField txtEmail;

    @FXML
    private MenuButton btOpcaoBusca;

    @FXML
    private TextField txtSenha;

    @FXML
    private TextField txtCpf;

    @FXML
    public void initialize() {
        invokerPopUpCommand.register("confirmarCadastro", new ConfirmarCadastroCommand());
    }

    @FXML
    protected void btVoltar(ActionEvent e) throws Exception {
        Main.changeScreen("login");
    }

    @FXML
    protected void btCadastrar(ActionEvent e) throws Exception{
        String nomeCompleto = txtNomeUsuario.getText();
        String[] partesNome = nomeCompleto.split(" ", 2);
        String primeiroNome = partesNome[0];
        String sobrenome = (partesNome.length > 1) ? partesNome[1] : "";
        
        BibliotecariaModel bibliotecariaModel = new BibliotecariaModel(primeiroNome, sobrenome, txtCpf.getText(), txtEmail.getText(), txtSenha.getText(), false);

    
        invokerPopUpCommand.invoke("confirmarCadastro", bibliotecariaModel);

    }

}
