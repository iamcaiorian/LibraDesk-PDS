package screens.view;

import java.util.Date;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import controller.Em_AtrasoController;
import controller.Main;
import model.EmprestimoModel;

public class EmAtrasoView {

    Em_AtrasoController emAtrasoController = new Em_AtrasoController();

     @FXML
    protected void btLeitores(ActionEvent e) throws Exception {
        Main.changeScreen("leitores");
    }

    @FXML
    protected void btEmprestimos(ActionEvent e) throws Exception {
        Main.changeScreen("emprestimos");
    }

    @FXML
    protected void btAcervo(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
    }

    @FXML
    protected void btPerfil(ActionEvent e) throws Exception {
        Main.changeScreen("perfil");
    }

    @FXML
    protected void btFuncionario(ActionEvent e) throws Exception {
        Main.changeScreen("funcionario");
    }
    
    @FXML
    private TableView<EmprestimoModel> emAtrasoTableView;
    
    @FXML
    private MenuButton btOpcaoBusca;
    
    @FXML
    private TextField txtCampoPesquisado;

    @FXML
    public void initialize() {

        MenuItem item1 = new MenuItem("Por leitor");
        MenuItem item2 = new MenuItem("Por titulo");

        item1.setOnAction(event -> handleOpcaoSelecionada(item1));
        item2.setOnAction(event -> handleOpcaoSelecionada(item2));

        btOpcaoBusca.getItems().addAll(item1, item2);

        TableColumn<EmprestimoModel, Integer> colIdEmprestimo = new TableColumn<>("Nº");
        colIdEmprestimo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdEmprestimo()).asObject());

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

        emAtrasoTableView.getColumns().addAll(colIdEmprestimo, colNomeLeitor, colCpfLeitor, colNomeLivro, colDataEmprestimo, colDataPrevDevolucao, colMulta, colStatus);
        atualizarTabela();

    }

     public void atualizarTabela() {
        List<EmprestimoModel> emprestimos = emAtrasoController.pegarEmprestimos();
        preencherTableViewEmprestimo(emprestimos);

    }

    private String getOpcaoBusca() {
        return btOpcaoBusca.getText();
    }

    @FXML
    protected void btBuscarEmprestimo(ActionEvent e) {
        List<EmprestimoModel> emprestimos = emAtrasoController.buscarEmprestimo(txtCampoPesquisado.getText(), getOpcaoBusca());
        preencherTableViewEmprestimo(emprestimos);
    }

    public void preencherTableViewEmprestimo(List<EmprestimoModel> emprestimos) {
        ObservableList<EmprestimoModel> emprestimosObservableList = FXCollections.observableArrayList(emprestimos);
        emAtrasoTableView.setItems(emprestimosObservableList);
    }

    private void handleOpcaoSelecionada(MenuItem menuItem) {
        // Atualiza o texto do MenuButton com o texto do item selecionado
        btOpcaoBusca.setText(menuItem.getText());

    }

    @FXML
    protected void btDebitarEmprestimo(ActionEvent e) {
        EmprestimoModel emprestimoSelecionado = emprestimosTableView.getSelectionModel().getSelectedItem()
        emAtrasoController.debitarEmprestimo(emprestimoSelecionado.getIdEmprestimo());
    }

}
