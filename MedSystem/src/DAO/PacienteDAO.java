/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import classes.Endereco;
import classes.Paciente;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bruno
 */
public class PacienteDAO {

    public boolean create(Paciente p, Endereco e) {
        int idEndereco = -1, idCidade = -1, idEstado = -1;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT idPaciente FROM Paciente WHERE cpf = ? or rg = ?");
            stmt.setString(1, p.getCpf());
            stmt.setString(2, p.getRg());
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                try {
                    stmt = con.prepareStatement("INSERT INTO Estado (uf, pais) VALUES (?, ?) ");
                    stmt.setString(1, e.getUf());
                    stmt.setString(2, e.getPais());
                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                try {
                    stmt = con.prepareStatement("SELECT idEstado FROM Estado WHERE uf = ? and  pais = ? ");
                    stmt.setString(1, e.getUf());
                    stmt.setString(2, e.getPais());
                    rs = stmt.executeQuery();
                    rs.next();
                    idEstado = rs.getInt("idEstado");
                    System.out.println("idEstado: " + idEstado);
                } catch (SQLException ex) {
                    Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                try {
                    stmt = con.prepareStatement("SELECT idCidade FROM Cidade WHERE nomeCidade = ? and  idEstado = ?");
                    stmt.setString(1, e.getNomeCidade());
                    stmt.setInt(2, idEstado);
                    rs = stmt.executeQuery();

                    if (!rs.isBeforeFirst()) {
                        stmt.close();
                        rs.close();
                        stmt = con.prepareStatement("INSERT INTO Cidade (nomeCidade, idEstado) VALUES (?, ?) ");
                        stmt.setString(1, e.getNomeCidade());
                        stmt.setInt(2, idEstado);
                        stmt.executeUpdate();

                        stmt.close();
                        stmt = con.prepareStatement("SELECT idCidade FROM Cidade WHERE nomeCidade = ? and  idEstado = ?");
                        stmt.setString(1, e.getNomeCidade());
                        stmt.setInt(2, idEstado);
                        rs = stmt.executeQuery();
                    }
                    rs.next();
                    idCidade = rs.getInt("idCidade");

                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                try {
                    stmt = con.prepareStatement("SELECT idEndereco FROM Endereco WHERE logradouro = ? and  numero = ? and cep = ? and bairro = ? and idCidade = ? ");
                    stmt.setString(1, e.getLogradouro());
                    stmt.setString(2, e.getNumero());
                    stmt.setString(3, e.getCep());
                    stmt.setString(4, e.getBairro());
                    stmt.setInt(5, idCidade);
                    rs = stmt.executeQuery();

                    if (!rs.isBeforeFirst()) {
                        stmt.close();
                        rs.close();
                        stmt = con.prepareStatement("INSERT INTO Endereco (logradouro, numero, complemento, cep, bairro, idCidade) VALUES (?, ?, ?, ?, ?, ?) ");
                        stmt.setString(1, e.getLogradouro());
                        stmt.setString(2, e.getNumero());
                        stmt.setString(3, e.getComplemento());
                        stmt.setString(4, e.getCep());
                        stmt.setString(5, e.getBairro());
                        stmt.setInt(6, idCidade);
                        stmt.executeUpdate();

                        stmt.close();
                        stmt = con.prepareStatement("SELECT idEndereco FROM Endereco WHERE logradouro = ? and  numero = ? and cep = ? and bairro = ? and idCidade = ? ");
                        stmt.setString(1, e.getLogradouro());
                        stmt.setString(2, e.getNumero());
                        stmt.setString(3, e.getCep());
                        stmt.setString(4, e.getBairro());
                        stmt.setInt(5, idCidade);
                        rs = stmt.executeQuery();
                    }
                    rs.next();
                    idEndereco = rs.getInt("idEndereco");

                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                try {
                    stmt = con.prepareStatement("INSERT INTO Paciente (nomePaciente, dataNasc, sexo, rg, cpf, profissao, filiacao, Endereco_idEndereco) VALUES (?, str_to_date(?,'%d/%m/%Y'), ?, ?, ?, ?, ?, ?) ");
                    stmt.setString(1, p.getNomePaciente());
                    stmt.setString(2, p.getDataNasc());
                    stmt.setString(3, p.getSexo());
                    stmt.setString(4, p.getRg());
                    stmt.setString(5, p.getCpf());
                    stmt.setString(6, p.getProfissao());
                    stmt.setString(7, p.getFiliacao());
                    stmt.setInt(8, idEndereco);
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                }
                JOptionPane.showMessageDialog(null, "Paciente cadastrado com sucesso!");

            } else {
                JOptionPane.showMessageDialog(null, "ERRO: Este CPF ou RG já está cadastrado!");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return true;
    }

    public List<Paciente> read(String nome) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Paciente> pacientes = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT idPaciente, nomePaciente, dataNasc, sexo, rg, cpf, profissao, filiacao, Endereco_idEndereco FROM Paciente WHERE nomePaciente LIKE ?");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente(rs.getString("nomePaciente"), rs.getString("dataNasc"), rs.getString("sexo"), rs.getString("rg"), rs.getString("cpf"), rs.getString("profissao"), rs.getString("filiacao"));

                p.setIdPaciente(rs.getInt("idPaciente"));
                p.setIdEndereco(rs.getInt("Endereco_idEndereco"));

                pacientes.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return pacientes;
    }

    public Paciente pesquisaPaciente(String cpf) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT Endereco_idEndereco,idPaciente,nomePaciente, date_format(dataNasc, '%d/%m/%Y'), sexo, rg, cpf, profissao, filiacao, pais, uf, nomeCidade, logradouro, numero, complemento, cep, bairro FROM PacEnd WHERE cpf = ?");
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            rs.next();
            Paciente p = new Paciente(rs.getString("nomePaciente"), rs.getString("date_format(dataNasc, '%d/%m/%Y')"), rs.getString("sexo"), rs.getString("rg"), rs.getString("cpf"), rs.getString("profissao"), rs.getString("filiacao"));
            Endereco end = new Endereco(rs.getString("pais"), rs.getString("uf"), rs.getString("nomeCidade"), rs.getString("logradouro"), rs.getString("numero"), rs.getString("complemento"), rs.getString("cep"), rs.getString("bairro"));
            p.setEnd(end);
            p.setIdPaciente(rs.getInt("idPaciente"));
            p.setIdEndereco(rs.getInt("Endereco_idEndereco"));

            return p;

        } catch (SQLException ex) {
            System.out.println("Erro no pesquisa paciente");
            //Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        //se nao retornou lá em cima, deu erro
        return null;
    }
    
    
    public Paciente pesquisaPacienteID(int idPaciente) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM PacEnd WHERE idPaciente = ?");
            stmt.setInt(1, idPaciente);
            rs = stmt.executeQuery();

            rs.next();
            Paciente p = new Paciente(rs.getString("nomePaciente"), rs.getString("dataNasc"), rs.getString("sexo"), rs.getString("rg"), rs.getString("cpf"), rs.getString("profissao"), rs.getString("filiacao"));
            Endereco end = new Endereco(rs.getString("pais"), rs.getString("uf"), rs.getString("nomeCidade"), rs.getString("logradouro"), rs.getString("numero"), rs.getString("complemento"), rs.getString("cep"), rs.getString("bairro"));
            p.setEnd(end);
            p.setIdPaciente(rs.getInt("idPaciente"));
            p.setIdEndereco(rs.getInt("Endereco_idEndereco"));

            return p;

        } catch (SQLException ex) {
            System.out.println("Erro no pesquisa paciente");
            //Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        //se nao retornou lá em cima, deu erro
        return null;
    }

    public void delete(String cpf) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("DELETE FROM Paciente WHERE cpf = ?");
            stmt.setString(1, cpf);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Removido com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel remover Paciente!");
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public boolean update(Paciente p, Endereco e, String cpfAntigo, String rgAntigo) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int idEndereco = -1, idCidade = -1, idEstado = -1;

        try {
            stmt = con.prepareStatement("SELECT idPaciente FROM Paciente WHERE (cpf = ?  and cpf <> ?) or (rg = ? and rg <> ?)");
            stmt.setString(1, p.getCpf());
            stmt.setString(2, cpfAntigo);
            stmt.setString(3, p.getRg());
            stmt.setString(4, rgAntigo);
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {

                try {
                    stmt = con.prepareStatement("INSERT INTO Estado (uf, pais) VALUES (?, ?) ");
                    stmt.setString(1, e.getUf());
                    stmt.setString(2, e.getPais());
                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                try {
                    stmt = con.prepareStatement("SELECT idEstado FROM Estado WHERE uf = ? and  pais = ? ");
                    stmt.setString(1, e.getUf());
                    stmt.setString(2, e.getPais());
                    rs = stmt.executeQuery();
                    rs.next();
                    idEstado = rs.getInt("idEstado");
                    System.out.println("idEstado: " + idEstado);
                } catch (SQLException ex) {
                    Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                try {
                    stmt = con.prepareStatement("SELECT idCidade FROM Cidade WHERE nomeCidade = ? and  idEstado = ?");
                    stmt.setString(1, e.getNomeCidade());
                    stmt.setInt(2, idEstado);
                    rs = stmt.executeQuery();

                    if (!rs.isBeforeFirst()) {
                        stmt.close();
                        rs.close();
                        stmt = con.prepareStatement("INSERT INTO Cidade (nomeCidade, idEstado) VALUES (?, ?) ");
                        stmt.setString(1, e.getNomeCidade());
                        stmt.setInt(2, idEstado);
                        stmt.executeUpdate();

                        stmt.close();
                        stmt = con.prepareStatement("SELECT idCidade FROM Cidade WHERE nomeCidade = ? and  idEstado = ?");
                        stmt.setString(1, e.getNomeCidade());
                        stmt.setInt(2, idEstado);
                        rs = stmt.executeQuery();
                    }
                    rs.next();
                    idCidade = rs.getInt("idCidade");

                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                try {
                    stmt = con.prepareStatement("SELECT idEndereco FROM Endereco WHERE logradouro = ? and  numero = ? and cep = ? and bairro = ? and idCidade = ? and complemento = ? ");
                    stmt.setString(1, e.getLogradouro());
                    stmt.setString(2, e.getNumero());
                    stmt.setString(3, e.getCep());
                    stmt.setString(4, e.getBairro());
                    stmt.setInt(5, idCidade);
                    stmt.setString(6, e.getComplemento());
                    rs = stmt.executeQuery();

                    if (!rs.isBeforeFirst()) {
                        stmt.close();
                        rs.close();
                        stmt = con.prepareStatement("INSERT INTO Endereco (logradouro, numero, complemento, cep, bairro, idCidade) VALUES (?, ?, ?, ?, ?, ?) ");
                        stmt.setString(1, e.getLogradouro());
                        stmt.setString(2, e.getNumero());
                        stmt.setString(3, e.getComplemento());
                        stmt.setString(4, e.getCep());
                        stmt.setString(5, e.getBairro());
                        stmt.setInt(6, idCidade);
                        stmt.executeUpdate();

                        stmt.close();
                        stmt = con.prepareStatement("SELECT idEndereco FROM Endereco WHERE logradouro = ? and  numero = ? and cep = ? and bairro = ? and idCidade = ? and complemento = ? ");
                        stmt.setString(1, e.getLogradouro());
                        stmt.setString(2, e.getNumero());
                        stmt.setString(3, e.getCep());
                        stmt.setString(4, e.getBairro());
                        stmt.setInt(5, idCidade);
                        stmt.setString(6, e.getComplemento());
                        rs = stmt.executeQuery();
                    }
                    rs.next();
                    idEndereco = rs.getInt("idEndereco");

                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

                //Paciente
                try {
                    stmt = con.prepareStatement("UPDATE Paciente SET nomePaciente = ?, dataNasc = str_to_date(?,'%d/%m/%Y'), sexo = ?, rg = ?, profissao = ?, filiacao = ?, cpf = ?, Endereco_idEndereco  = ? WHERE cpf = ?");
                    stmt.setString(1, p.getNomePaciente());
                    stmt.setString(2, p.getDataNasc());
                    stmt.setString(3, p.getSexo());
                    stmt.setString(4, p.getRg());
                    stmt.setString(5, p.getProfissao());
                    stmt.setString(6, p.getFiliacao());
                    stmt.setString(7, p.getCpf());
                    stmt.setInt(8, idEndereco);
                    stmt.setString(9, cpfAntigo);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Paciente atualizado com sucesso!");
                    return true;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel atualizar Paciente!");
                } finally {
                    ConnectionFactory.closeConnection(con, stmt, rs);
                }
            }else{
                JOptionPane.showMessageDialog(null, "ERRO: Este CPF ou RG já está cadastrado!");
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return false;
    }
}
