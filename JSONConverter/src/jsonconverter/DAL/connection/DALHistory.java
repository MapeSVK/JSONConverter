/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsonconverter.BE.History;

/**
 *
 * @author Mape
 */
public class DALHistory {

    /* OBJECT POOL */
    // Create the ConnectionPool:
    private JDBCConnectionPool pool = new JDBCConnectionPool(
            "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://10.176.111.31;databaseName=JSONConverter",
            "CS2017B_27_java", "javajava");

    /* GET ALL HISTORY */
    public List<History> getAllHistory() {
        List<History> history = new ArrayList();

        try (Connection con = pool.checkOut()) {

            PreparedStatement pstmt = con.prepareCall("SELECT * FROM History");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                History h = new History(rs.getString("history_date_time"),
                        rs.getInt("history_id"),
                        rs.getString("local_username"),
                        rs.getString("action_message"),
                        rs.getBoolean("has_error"),
                        rs.getString("error_message"));

                history.add(h);
            }

            pool.checkIn(con);
        } catch (SQLException ex) {
            Logger.getLogger(DALHistory.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return history;
    }
    
    
    /* adding history to a database */
    public void addHistory(History history) {
       try (Connection con = pool.checkOut()) {
            String sql
                    = "INSERT INTO History"
                    + "(local_username,action_message,history_date_time,has_error,error_message) "
                    + "VALUES(?,?,?,?,?)";
            PreparedStatement pstmt
                    = con.prepareStatement(
                            sql, Statement.RETURN_GENERATED_KEYS);
            
           
            pstmt.setString(1, history.getUsername());
            pstmt.setString(2, history.getFileName());
            pstmt.setString(3, history.getDateAndTime());
            pstmt.setBoolean(4, history.isHasError());
            pstmt.setString(5, history.getErrorMessage());
            
            

            int affected = pstmt.executeUpdate();
            
            // Get database generated id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                history.setId(rs.getInt(1));
            }
            
             pool.checkIn(con);
        }
        catch (SQLException ex) {
            Logger.getLogger(DALHistory.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}
