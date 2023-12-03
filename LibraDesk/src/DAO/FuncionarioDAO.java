package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import conexaoDAO.Conexao;
import model.BibliotecariaModel;
import model.PessoaModel;

public class FuncionarioDAO implements IDAO{

    Conexao conSing = Conexao.getInstancy();
    Connection conexao = conSing.getConexao();

    public List<BibliotecariaModel> getFuncionarios(){
        Conexao conSing = Conexao.getInstancy();
        Connection conexao = conSing.getConexao();
        List<BibliotecariaModel> listaBibliotecaria = new ArrayList<>();

        try{
            String sql = "SELECT * FROM bibliotecaria b JOIN pessoa p ON b.cpf = p.cpf";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                BibliotecariaModel bibliotecaria = new BibliotecariaModel(
                    rs.getString("pnome"),
                    rs.getString("sobrenome"),
                    rs.getString("cpf"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getBoolean("coordenador")
                );
                

                listaBibliotecaria.add(bibliotecaria);
            }

        }catch (SQLException exececaoAcervo) {
            exececaoAcervo.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + exececaoAcervo.getMessage());
        }

        return listaBibliotecaria;
    }

    public void cadastrarPessoa(PessoaModel pessoa){

        try{
            String sql = "INSERT INTO pessoa(pnome, sobrenome, cpf) VALUES(?,?,?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, pessoa.getPnome());
            stmt.setString(2, pessoa.getSobrenome());
            stmt.setString(3, pessoa.getCpf());

            stmt.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar pessoa: " + ex);

        }

    }

    public void ExcluirFuncionario(String cpf){

        try{
            String sql = "DELETE FROM pessoa WHERE cpf = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cpf);

            stmt.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir funcionario: " + ex);
        }
    }

    public void CadastrarBibliotecaria(BibliotecariaModel bibliotecaria){
        Conexao conSing = Conexao.getInstancy();
        Connection conexao = conSing.getConexao();

        try{
            String sql = "INSERT INTO bibliotecaria(email, senha, coordenador, cpf) VALUES(?,?,?,?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, bibliotecaria.getEmail());
            stmt.setString(2, bibliotecaria.getSenha());
            stmt.setBoolean(3, false);
            stmt.setString(4, bibliotecaria.getCpf());

            stmt.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar bibliotecaria: " + ex);
        }

    }
}
