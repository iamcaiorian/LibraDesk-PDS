package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import conexaoDAO.Conexao;
import model.EmprestimoModel;
import strategy.MultaEspecial;
import strategy.MultaPadrao;

public class EmprestimoDAO implements IDAO{
    
    private Conexao conSing = Conexao.getInstancy();
    private Connection conexao = conSing.getConexao();


    public List<EmprestimoModel> buscarEmprestimoPorLeitor(String nomeLeitor){

        System.out.println("dao");
        
        List<EmprestimoModel> listaEmprestimo = new ArrayList<>();
        
        try{
            
            String sql = "SELECT * FROM emprestimo e JOIN pessoa p ON e.cpf_leitor = p.cpf JOIN livro l ON l.id = e.id_livro WHERE (p.pnome || ' ' || p.sobrenome) LIKE ? AND "
                    + "e.status = true ";           
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, "%" + nomeLeitor + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                EmprestimoModel emprestimo = new EmprestimoModel(
                        resultSet.getString("pnome") + " " + resultSet.getString("sobrenome"),
                        resultSet.getDate("data_emprestimo"),
                        resultSet.getDate("data_prev_dev"),
                        resultSet.getDate("data_real_dev"),
                        resultSet.getDouble("multa"),
                        resultSet.getString("cpf_leitor"),
                        resultSet.getString("titulo"),
                        resultSet.getInt("id_livro"),
                        resultSet.getBoolean("status"),
                        resultSet.getInt("id_emprestimo")
                );
            
                listaEmprestimo.add(emprestimo);
            }
            
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + excecaoLeitor.getMessage());
        }
        
        return listaEmprestimo;
    }

    public List<EmprestimoModel> buscarEmprestimoPorLivro(String tituloLivro){
        
        List<EmprestimoModel> listaEmprestimo = new ArrayList<>();
        
        try{
            
            String sql = "SELECT * FROM emprestimo e JOIN pessoa p ON e.cpf_leitor = p.cpf JOIN livro l ON l.id = e.id_livro WHERE l.titulo LIKE ? AND e.status = true";           
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, "%" + tituloLivro + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                EmprestimoModel emprestimo = new EmprestimoModel(
                        resultSet.getString("pnome") + " " + resultSet.getString("sobrenome"),
                        resultSet.getDate("data_emprestimo"),
                        resultSet.getDate("data_prev_dev"),
                        resultSet.getDate("data_real_dev"),
                        resultSet.getDouble("multa"),
                        resultSet.getString("cpf_leitor"),
                        resultSet.getString("titulo"),
                        resultSet.getInt("id_livro"),
                        resultSet.getBoolean("status"),
                        resultSet.getInt("id_emprestimo")
                );
                
            
                listaEmprestimo.add(emprestimo);
            }
            
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + excecaoLeitor.getMessage());
        }
        
        return listaEmprestimo;
    }


    public void atualizarMultas(){
        LocalDate dataAtual = LocalDate.now();

        try{
            
            String sql = "SELECT * FROM emprestimo";           
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Date dataPrevDevolucao = resultSet.getDate("data_prev_dev");
                LocalDate dataPrevDevolucaoLocalDate = new java.sql.Date(dataPrevDevolucao.getTime()).toLocalDate();
                
                
                long diferencaEmDias = calcularDiferencaDias(dataPrevDevolucaoLocalDate, dataAtual);

                double multa = 0;
                if(diferencaEmDias <= 30) {
                    MultaPadrao multaPadrao = new MultaPadrao();
                    multa = multaPadrao.calcularMulta(diferencaEmDias);
                } else {
                    MultaEspecial multaEspecial = new MultaEspecial();
                    multa = multaEspecial.calcularMulta(diferencaEmDias);
                }

                String sql2 = "UPDATE emprestimo SET multa = ? WHERE id_emprestimo = ?";
                PreparedStatement preparedStatement2 = conexao.prepareStatement(sql2);
                preparedStatement2.setDouble(1, multa);
                preparedStatement2.setInt(2, resultSet.getInt("id_emprestimo"));
                preparedStatement2.executeUpdate();
            }
            
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado 2 " + excecaoLeitor.getMessage());
        }
        

    }

    public static long calcularDiferencaDias(LocalDate data1, LocalDate data2) {
        // Calcula a diferença em dias usando ChronoUnit
        return ChronoUnit.DAYS.between(data1, data2);
    }

    public List<EmprestimoModel> pegarEmprestimos(){
        
        List<EmprestimoModel> listaEmprestimo = new ArrayList<>();
        
        try{
            
            String sql = "SELECT * FROM emprestimo e JOIN pessoa p ON e.cpf_leitor = p.cpf JOIN livro l ON l.id = e.id_livro WHERE e.status = true";           
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                
                EmprestimoModel emprestimo = new EmprestimoModel(
                        resultSet.getString("pnome") + " " + resultSet.getString("sobrenome"),
                        resultSet.getDate("data_emprestimo"),
                        resultSet.getDate("data_prev_dev"),
                        resultSet.getDate("data_real_dev"),
                        resultSet.getDouble("multa"),
                        resultSet.getString("cpf_leitor"),
                        resultSet.getString("titulo"),
                        resultSet.getInt("id_livro"),
                        resultSet.getBoolean("status"),
                        resultSet.getInt("id_emprestimo")
                );
                
            
                listaEmprestimo.add(emprestimo);
            }
            
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado 3 " + excecaoLeitor.getMessage());
        }
        
        return listaEmprestimo;
    }

    public void debitarEmprestimo(int idEmprestimo){
        try{
            String sql = "UPDATE Emprestimo SET status = false WHERE id_emprestimo = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, idEmprestimo);
            preparedStatement.executeUpdate();
        
        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado 4 " + excecaoLeitor.getMessage());
        }
    }
    
    public void atualizarStatus(int idEmprestimo){
        
        try{
            String sql = "UPDATE Emprestimo SET status = false WHERE id_emprestimo = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, idEmprestimo);
            preparedStatement.executeUpdate();

        } catch (SQLException excecaoLeitor) {
            excecaoLeitor.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado 5 " + excecaoLeitor.getMessage());
        }
    }

    int idLivro;

    public void adicionarEmprestimo(Date dataEmprestimo, Date dataPrevDevolucao, Date dataRealDevolucao, double multa, String cpfLeitor, String nomeLivro, int idLivro){
        
        try{
            Conexao conSing = Conexao.getInstancy();
            Connection conexao = conSing.getConexao();
            
            idLivro = pegarIdLivro(nomeLivro);
            String sql = "INSERT INTO emprestimo (data_emprestimo, data_prev_dev, data_real_dev, multa, cpf_leitor, id_livro, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);        
            preparedStatement.setDate(1, new java.sql.Date(dataEmprestimo.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(dataPrevDevolucao.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(dataRealDevolucao.getTime()));
            preparedStatement.setDouble(4, multa);
            preparedStatement.setString(5, cpfLeitor);
            preparedStatement.setInt(6, idLivro);
            preparedStatement.setBoolean(7, true);
            
            
            preparedStatement.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + ex.getMessage());
        }
        
    }

    public int pegarIdLivro(String nomeLivro){
        int idLivro = -1;
        
        try{
            Conexao conSing = Conexao.getInstancy();
            Connection conexao = conSing.getConexao();
            
            String sql = "SELECT id FROM livro WHERE titulo = ?";
            
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, nomeLivro);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idLivro = resultSet.getInt("id");
                // Agora, 'idLivro' contém o valor do id do primeiro livro encontrado com o título fornecido.
            } else {
                // Caso não haja resultados para o título fornecido.
                JOptionPane.showMessageDialog(null, "Livro nao existe");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deu errado: " + ex.getMessage());
        }
        
        return idLivro;
        
    }
}
