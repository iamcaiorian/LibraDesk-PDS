package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import conexaoDAO.Conexao;
import controller.Main;
import model.LeitorModel;
import model.PessoaModel;

public class LeitoresDAO implements IDAO{
    Conexao conSing = Conexao.getInstancy();
    Connection conexao = conSing.getConexao();

    public List<LeitorModel> pegarLeitores() {
        List<LeitorModel> listaLeitores = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Leitor l JOIN Pessoa p on l.cpf = p.cpf";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSetLeitor = preparedStatement.executeQuery();

            while (resultSetLeitor.next()) {
                LeitorModel leitor = new LeitorModel(
                        resultSetLeitor.getString("pnome"),
                        resultSetLeitor.getString("sobrenome"),
                        resultSetLeitor.getString("cpf"),
                        resultSetLeitor.getString("telefone_um"),
                        resultSetLeitor.getString("telefone_dois"),
                        resultSetLeitor.getString("bairro"),
                        resultSetLeitor.getString("rua"),
                        resultSetLeitor.getString("cidade"),
                        resultSetLeitor.getInt("numero"));
                listaLeitores.add(leitor);
            }
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + excecaoLeitor.getMessage());
        }

        return listaLeitores;
    }

    public List<LeitorModel> pesquisarLeitorPorNome(String nomeLeitor) {
        List<LeitorModel> listaLeitores = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Leitor l JOIN pessoa p ON l.cpf = p.cpf WHERE (p.pnome || ' ' || p.sobrenome) LIKE ? ";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, "%" + nomeLeitor + "%");
            ResultSet resultSetLeitor = preparedStatement.executeQuery();

            while (resultSetLeitor.next()) {
                LeitorModel leitor = new LeitorModel(
                        resultSetLeitor.getString("pnome"),
                        resultSetLeitor.getString("sobrenome"),
                        resultSetLeitor.getString("cpf"),
                        resultSetLeitor.getString("telefone_um"),
                        resultSetLeitor.getString("telefone_dois"),
                        resultSetLeitor.getString("bairro"),
                        resultSetLeitor.getString("rua"),
                        resultSetLeitor.getString("cidade"),
                        resultSetLeitor.getInt("numero"));
                listaLeitores.add(leitor);
            }
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + excecaoLeitor.getMessage());
        }

        return listaLeitores;
    }

    public void excluirLeitor(String cpfLeitor) {
        Conexao conSing = Conexao.getInstancy();
        Connection conexao = conSing.getConexao();

        try {
            String sql = "DELETE FROM Leitor WHERE cpf = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, cpfLeitor);
            preparedStatement.executeUpdate();
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + excecaoLeitor.getMessage());
        }
    }

    public void adicionarPessoa(PessoaModel pessoa) {
        try {
            Conexao conSing = Conexao.getInstancy();
            Connection conexao = conSing.getConexao();
            String sql = "INSERT INTO Pessoa(pnome, sobrenome, cpf) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, pessoa.getPnome());
            preparedStatement.setString(2, pessoa.getSobrenome());
            preparedStatement.setString(3, pessoa.getCpf());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + ex.getMessage());
        }
    }

    public void adicionarLeitor(LeitorModel leitor) {
        try {
            Conexao conSing = Conexao.getInstancy();
            Connection conexao = conSing.getConexao();

            String sql = "INSERT INTO Leitor (telefone_um, telefone_dois, cpf, bairro, rua, cidade, numero) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, leitor.getTelefoneUm());
            preparedStatement.setString(2, leitor.getTelefoneDois());
            preparedStatement.setString(3, leitor.getCpf());
            preparedStatement.setString(4, leitor.getBairro());
            preparedStatement.setString(5, leitor.getRua());
            preparedStatement.setString(6, leitor.getCidade());
            preparedStatement.setInt(7, leitor.getNumero());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + ex.getMessage());
        }
    }

    public void editarLeitor(LeitorModel leitor) throws Exception {
        try {

            String sql = "UPDATE Leitor SET telefone_um = ?, telefone_dois = ?, bairro = ?, rua = ?, cidade = ?, numero = ? WHERE cpf = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, leitor.getTelefoneUm());
            preparedStatement.setString(2, leitor.getTelefoneDois());
            preparedStatement.setString(3, leitor.getBairro());
            preparedStatement.setString(4, leitor.getRua());
            preparedStatement.setString(5, leitor.getCidade());
            preparedStatement.setInt(6, leitor.getNumero());
            preparedStatement.setString(7, leitor.getCpf());

            preparedStatement.executeUpdate();
            Main.changeScreen("leitores");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + ex.getMessage());
        }
    }

    public void editarPessoa(PessoaModel pessoa) {
        try {
            Conexao conSing = Conexao.getInstancy();
            Connection conexao = conSing.getConexao();
            String sql = "UPDATE Pessoa SET Pnome = ?, sobrenome = ? WHERE cpf = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, pessoa.getPnome());
            preparedStatement.setString(2, pessoa.getSobrenome());
            preparedStatement.setString(3, pessoa.getCpf());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + ex.getMessage());
        }
    }
}
