/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import conexaoDAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

import DAO.EmprestimoDAO;
import model.LivroModel;
import observer.IObservador;
import model.EmprestimoModel;
import model.LeitorModel;
import strategy.CalculadoraMulta;
import strategy.MultaEspecial;
import strategy.MultaPadrao;

/**
 * FXML Controller class
 *
 * @author CAIO
 */
public class EmprestimosController implements IController {

    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    @FXML
    protected void btLeitores(ActionEvent e)throws Exception  {
        Main.changeScreen("leitores");
    }

    @FXML
    protected void btAcervo(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
    }

    @FXML
    protected void btEmAtraso(ActionEvent e)throws Exception  {
        Main.changeScreen("em_atraso");
    }

    @FXML
    protected void btNovoEmprestimo(ActionEvent e) throws Exception {
        openNovoEmprestimoPopup();
    }

    @FXML
    protected void btFuncionario(ActionEvent e) throws Exception {
        Main.changeScreen("funcionario");
    }

    @FXML
    protected void btPerfil(ActionEvent e) throws Exception {
        Main.changeScreen("perfil");
    }

    @FXML
    private TableView<EmprestimoModel> emprestimosTableView;

    @FXML
    private MenuButton btOpcaoBusca;

    @FXML
    private TextField txtCampoPesquisado;

    private void openNovoEmprestimoPopup() {
        try {
            // Carregando o arquivo FXML da tela NovoLivro
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/NovoEmprestimo.fxml"));
            Parent root = loader.load();

            // Obtendo o controller da tela NovoLivro
            NovoEmprestimoController controller = loader.getController();
            controller.setEmprestimoController(this);

            // Criando um novo palco (Stage) para a tela NovoLivro
            Stage novoEmprestimoStage = new Stage();
            novoEmprestimoStage.setTitle("Novo Livro");
            novoEmprestimoStage.initStyle(StageStyle.UTILITY);
            novoEmprestimoStage.initModality(Modality.APPLICATION_MODAL);
            novoEmprestimoStage.setScene(new Scene(root, 992, 614));

            // Exibindo o palco
            novoEmprestimoStage.showAndWait();
        } catch (Exception e) {
            // Tratamento de exceção (substitua por um tratamento adequado)
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        MenuItem item1 = new MenuItem("Por leitor");
        MenuItem item2 = new MenuItem("Por titulo");

        item1.setOnAction(event -> handleOpcaoSelecionada(item1));
        item2.setOnAction(event -> handleOpcaoSelecionada(item2));

        btOpcaoBusca.getItems().addAll(item1, item2);

        TableColumn<EmprestimoModel, Integer> colIdEmprestimo = new TableColumn<>("Nº");
        colIdEmprestimo
                .setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdEmprestimo()).asObject());

        TableColumn<EmprestimoModel, String> colNomeLeitor = new TableColumn<>("Nome Leitor");
        colNomeLeitor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomeLeitor()));

        TableColumn<EmprestimoModel, String> colCpfLeitor = new TableColumn<>("CPF Leitor");
        colCpfLeitor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCpfLeitor()));

        TableColumn<EmprestimoModel, String> colNomeLivro = new TableColumn<>("Nome Livro");
        colNomeLivro.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomeLivro()));

        TableColumn<EmprestimoModel, Date> colDataEmprestimo = new TableColumn<>("Data Empréstimo");
        colDataEmprestimo.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDataEmprestimo()));

        TableColumn<EmprestimoModel, Date> colDataPrevDevolucao = new TableColumn<>("Data Devolução Prevista");
        colDataPrevDevolucao.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDataPrevDev()));

        TableColumn<EmprestimoModel, Double> colMulta = new TableColumn<>("Multa");
        colMulta.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMulta()));

        TableColumn<EmprestimoModel, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        emprestimosTableView.getColumns().addAll(colIdEmprestimo, colNomeLeitor, colCpfLeitor, colNomeLivro,
                colDataEmprestimo, colDataPrevDevolucao, colMulta, colStatus);
        emprestimoDAO.atualizarMultas();
        atualizarTabela();

    }

    public void atualizarTabela() {
        List<EmprestimoModel> emprestimos = emprestimoDAO.pegarEmprestimos();
        preencherTableViewEmprestimo(emprestimos);

    }

    private String getOpcaoBusca() {
        return btOpcaoBusca.getText();
    }

    @FXML
    protected void btBuscarEmprestimo(ActionEvent action) {

        if (getOpcaoBusca() == "Por leitor") {
            List<EmprestimoModel> emprestimos = emprestimoDAO.buscarEmprestimoPorLeitor(txtCampoPesquisado.getText());
            preencherTableViewEmprestimo(emprestimos);
        } else if (getOpcaoBusca() == "Por titulo") {
            List<EmprestimoModel> emprestimos = emprestimoDAO.buscarEmprestimoPorLivro(txtCampoPesquisado.getText());
            preencherTableViewEmprestimo(emprestimos);
        } else {
            JOptionPane.showMessageDialog(null, "selecione uma forma de pesquisa desejado");
        }

    }

    public void preencherTableViewEmprestimo(List<EmprestimoModel> emprestimos) {
        ObservableList<EmprestimoModel> emprestimosObservableList = FXCollections.observableArrayList(emprestimos);
        emprestimosTableView.setItems(emprestimosObservableList);
    }

    private void handleOpcaoSelecionada(MenuItem menuItem) {
        // Atualiza o texto do MenuButton com o texto do item selecionado
        btOpcaoBusca.setText(menuItem.getText());

    }

    @FXML
    protected void btDebitarEmprestimo(ActionEvent e) {
        EmprestimoModel emprestimoSelecionado = emprestimosTableView.getSelectionModel().getSelectedItem();

        emprestimoDAO.debitarEmprestimo(emprestimoSelecionado.getIdEmprestimo());
    }

}
