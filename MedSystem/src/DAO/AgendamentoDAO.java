/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import classes.Agendamento;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bruno
 */
public class AgendamentoDAO {

    public boolean create(Agendamento a, String cpf) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            stmt = con.prepareStatement("SELECT * FROM Agendamento WHERE dataAgendamento = str_to_date(?,'%d/%m/%Y') AND horaAgendamento between SUBTIME(TIME_FORMAT(?, '%H:%i'), '00:15:00') and addtime(TIME_FORMAT(?, '%H:%i'), '00:15:00');");
            stmt.setString(1, a.getDataAgendamento());
            stmt.setString(2, a.getHoraAgendamento());
            stmt.setString(3, a.getHoraAgendamento());
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                try {
                    stmt = con.prepareStatement("INSERT INTO Agendamento (dataAgendamento, horaAgendamento, tipoAgendamento, idSecretaria, idPaciente) VALUES (str_to_date(?,'%d/%m/%Y'),TIME_FORMAT(?, '%H:%i'),?,?,(select idPaciente from Paciente where cpf = ?))");
                    stmt.setString(1, a.getDataAgendamento());
                    stmt.setString(2, a.getHoraAgendamento());
                    stmt.setString(3, a.getTipoAgendamento());
                    stmt.setInt(4, SecretariaDAO.getIdSecretaria());
                    stmt.setString(5, cpf);

                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    stmt.close();
                    rs.close();
                }

            } else {
                JOptionPane.showMessageDialog(null, "ERRO: Esse horário já esta ocupado!");
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

    public List<Agendamento> readNome(String nome) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Agendamento> agendamentos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT a.idAgendamento, a.dataAgendamento, a.horaAgendamento, a.tipoAgendamento, a.presenca, a.obsAgenda, a.idSecretaria, p.nomePaciente FROM Agendamento a INNER JOIN Paciente p ON (a.idPaciente = p.idPaciente) WHERE p.nomePaciente LIKE ?");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Agendamento a = new Agendamento(rs.getString("a.horaAgendamento"), rs.getString("a.dataAgendamento"), rs.getString("a.tipoAgendamento"), rs.getString("p.nomePaciente"), rs.getInt("a.idAgendamento"), rs.getInt("a.presenca"));

                //a.setIdPaciente(rs.getInt("idPaciente"));
                agendamentos.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return agendamentos;
    }

    public List<Agendamento> readData(java.util.Date dataPesquisada) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        //passando para Data sql
        Date data = new Date(dataPesquisada.getTime());

        List<Agendamento> agendamentos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT a.idAgendamento, a.dataAgendamento, a.horaAgendamento, a.tipoAgendamento, a.presenca, a.obsAgenda, a.idSecretaria, p.nomePaciente FROM Agendamento a INNER JOIN Paciente p ON (a.idPaciente = p.idPaciente) WHERE a.dataAgendamento = ?");
            stmt.setString(1, data.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {

                Agendamento a = new Agendamento(rs.getString("a.horaAgendamento"), rs.getString("a.dataAgendamento"), rs.getString("a.tipoAgendamento"), rs.getString("p.nomePaciente"), rs.getInt("a.idAgendamento"), rs.getInt("a.presenca"));

                //a.setIdPaciente(rs.getInt("idPaciente"));
                agendamentos.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return agendamentos;
    }

    public void delete(String data, String hora) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("DELETE FROM Agendamento WHERE dataAgendamento = ? AND horaAgendamento = ?");
            stmt.setString(1, data);
            stmt.setString(2, hora);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Removido com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel remover Paciente!");
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public Agendamento pesquisaAgendamento(String data, String hora) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT idPaciente, dataAgendamento, horaAgendamento, tipoAgendamento FROM Agendamento WHERE dataAgendamento = ? AND horaAgendamento = ?");
            stmt.setString(1, data);
            stmt.setString(2, hora);
            rs = stmt.executeQuery();

            rs.next();
            Agendamento a = new Agendamento(rs.getString("horaAgendamento"), rs.getString("dataAgendamento"), rs.getString("tipoAgendamento"), rs.getInt("idPaciente"));

            return a;

        } catch (SQLException ex) {
            System.out.println("Erro no pesquisa paciente");
            //Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        //se nao retornou lá em cima, deu erro
        return null;
    }

    public boolean update(Agendamento a, String dataAntiga, String horaAntiga, String cpf) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM Agendamento WHERE dataAgendamento = str_to_date(?,'%d/%m/%Y') AND horaAgendamento between SUBTIME(TIME_FORMAT(?, '%H:%i'), '00:15:00') and addtime(TIME_FORMAT(?, '%H:%i'), '00:15:00') AND dataAgendamento <> str_to_date(?,'%d/%m/%Y') AND horaAgendamento <> TIME_FORMAT(?, '%H:%i');");
            stmt.setString(1, a.getDataAgendamento());
            stmt.setString(2, a.getHoraAgendamento());
            stmt.setString(3, a.getHoraAgendamento());
            stmt.setString(4, a.getDataAgendamento());
            stmt.setString(5, a.getHoraAgendamento());
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {

                //Paciente
                try {
                    stmt = con.prepareStatement("UPDATE Agendamento SET idPaciente = (select idPaciente from Paciente p where p.cpf = ?), dataAgendamento = str_to_date(?,'%d/%m/%Y'), horaAgendamento = TIME_FORMAT(?, '%H:%i'), tipoAgendamento = ? WHERE dataAgendamento = ? AND horaAgendamento = ?");
                    stmt.setString(1, cpf);
                    stmt.setString(2, a.getDataAgendamento());
                    stmt.setString(3, a.getHoraAgendamento());
                    stmt.setString(4, a.getTipoAgendamento());
                    stmt.setString(5, dataAntiga);
                    stmt.setString(6, horaAntiga);

                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Agendamento atualizado com sucesso!");
                    return true;

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel atualizar o Agendamento!");
                } finally {
                    ConnectionFactory.closeConnection(con, stmt, rs);
                }
                
                
            } else {
                JOptionPane.showMessageDialog(null, "ERRO: Esse horário já esta ocupado!");
                return false;
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Não foi possivel checar o Agendamento!");
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return false;
    }
}
