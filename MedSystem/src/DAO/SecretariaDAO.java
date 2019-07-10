/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConnectionFactory;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author bruno
 */
public class SecretariaDAO {
    private static int idSecretaria = 0;

    
    public boolean read(String login, String senha){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        boolean verificar = false;
        
        try {            
            stmt = con.prepareStatement("SELECT idSecretaria FROM Secretaria WHERE loginSecretaria = ? and senhaSecretaria = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                verificar = true;
                idSecretaria = rs.getInt("idSecretaria");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return verificar;
    }
    
    public static int getIdSecretaria() {
        return idSecretaria;
    }
    
    
}
