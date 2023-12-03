package screens.view;

import java.util.Date;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import controller.Main;
import controller.NovoEmprestimoController;
import model.EmprestimoModel;
import controller.EmprestimosController;

public class EmprestimosView {

    EmprestimosController emprestimoController = new EmprestimosController();

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
        Main.changeScreen("novoEmprestimo");
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

    // private void openNovoEmprestimoPopup() {
    //     try {
    //         // Carregando o arquivo FXML da tela NovoLivro
    //         FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/NovoEmprestimo.fxml"));
    //         Parent root = loader.load();

    //         // Obtendo o controller da tela NovoLivro
    //         NovoEmprestimoController controller = loader.getController();
    //         controller.setEmprestimoController(this);

    //         // Criando um novo palco (Stage) para a tela NovoLivro
    //         Stage novoEmprestimoStage = new Stage();
    //         novoEmprestimoStage.setTitle("Novo Livro");
    //         novoEmprestimoStage.initStyle(StageStyle.UTILITY);
    //         novoEmprestimoStage.initModality(Modality.APPLICATION_MODAL);
    //         novoEmprestimoStage.setScene(new Scene(root, 992, 614));

    //         // Exibindo o palco
    //         novoEmprestimoStage.showAndWait();
    //     } catch (Exception e) {
    //         // Tratamento de exceção (substitua por um tratamento adequado)
    //         e.printStackTrace();
    //     }
    // }

    
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
        emprestimoController.atualizarMultas();
        atualizarTabela();

    }

    public void atualizarTabela() {
        List<EmprestimoModel> emprestimos = emprestimoController.pegarEmprestimos();
        preencherTableViewEmprestimo(emprestimos);
    }

    public void preencherTableViewEmprestimo(List<EmprestimoModel> emprestimos) {
        ObservableList<EmprestimoModel> emprestimosObservableList = FXCollections.observableArrayList(emprestimos);
        emprestimosTableView.setItems(emprestimosObservableList);
    }

    private String getOpcaoBusca() {
        return btOpcaoBusca.getText();
    }

    @FXML
    protected void btBuscarEmprestimo(ActionEvent e) {
        List<EmprestimoModel> emprestimos = emprestimoController.buscarEmprestimo(txtCampoPesquisado.getText(), getOpcaoBusca());
        preencherTableViewEmprestimo(emprestimos);
    }

    private void handleOpcaoSelecionada(MenuItem menuItem) {
        // Atualiza o texto do MenuButton com o texto do item selecionado
        btOpcaoBusca.setText(menuItem.getText());
    }

    @FXML
    protected void btDebitarEmprestimo(ActionEvent e) {
        EmprestimoModel emprestimoSelecionado = emprestimosTableView.getSelectionModel().getSelectedItem();
        emprestimoController.debitarEmprestimo(emprestimoSelecionado.getIdEmprestimo());
    }
}
