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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import jsonconverter.BE.Config;

/**
 *
 * @author Pepe15224
 */
public class DALConfig {

    private JDBCConnectionPool pool = new JDBCConnectionPool(
            "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://10.176.111.31;databaseName=JSONConverter",
            "CS2017B_27_java", "javajava");

    public List<Config> getAllConfigs(String username) {
        List<Config> configList = new ArrayList();

        try (Connection con = pool.checkOut()) {
            PreparedStatement pstmt = con.prepareCall("SELECT * FROM Config Where privacy=? AND creator_name=? OR privacy=?");
            pstmt.setString(1, "True");
            pstmt.setString(2, username);
            pstmt.setString(3, "False");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Config cnfg = new Config();
                cnfg.setCinfig_id(rs.getInt("config_id"));
                cnfg.setSiteName(rs.getString("siteName"));
                cnfg.setAssetSerialNumber(rs.getString("assetSerialNumber"));
                cnfg.setType(rs.getString("type"));
                cnfg.setExternalWorkOrderId(rs.getString("externalWorkOrderId"));
                cnfg.setSystemStatus(rs.getString("systemStatus"));
                cnfg.setUserStatus(rs.getString("userStatus"));
                cnfg.setCreatedOn(rs.getString("createdOn"));
                cnfg.setCreatedBy(rs.getString("createdBy"));
                cnfg.setName(rs.getString("name"));
                cnfg.setPriority(rs.getString("priority"));
                cnfg.setStatus(rs.getString("status"));
                cnfg.setLatestFinishDate(rs.getString("latestFinishDate"));
                cnfg.setEarliestStartDate(rs.getString("earliestStartDate"));
                cnfg.setLatestStartDate(rs.getString("latestStartDate"));
                cnfg.setEstimatedTime(rs.getString("estimatedTime"));
                cnfg.setConfigName(rs.getString("config_name"));
                cnfg.setPrivacy(rs.getBoolean("privacy"));
                cnfg.setCreatorName(rs.getString("creator_name"));
                configList.add(cnfg);
            }

            pool.checkIn(con);
        } catch (SQLException ex) {
            Logger.getLogger(DALHistory.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return configList;
    }

    public void removeConfigFromDatabase(Config removeConfig) {

        try (Connection con = pool.checkOut()) {
            String sql = "DELETE FROM Config WHERE config_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, removeConfig.getCinfig_id());
            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Config could not be removed");
            } else {
                System.out.println("Config removed correctly");

            }
            pool.checkIn(con);
        } catch (SQLException ex) {
            Logger.getLogger(DALHistory.class
                    .getName()).log(
                            Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Config could not be removed");

        }
    }

    public void saveConfigToDatabase(Config config) {

        try (Connection con = pool.checkOut()) {
            String sql = "INSERT INTO Config "
                    + "(siteName, assetSerialNumber, type, externalWorkOrderId, systemStatus, "
                    + "userStatus, createdOn, createdBy, name, priority, status, latestFinishDate, earliestStartDate, "
                    + "latestStartDate, estimatedTime, config_name, privacy, creator_name) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, config.getSiteName());
            pstmt.setString(2, config.getAssetSerialNumber());
            pstmt.setString(3, config.getType());
            pstmt.setString(4, config.getExternalWorkOrderId());
            pstmt.setString(5, config.getSystemStatus());
            pstmt.setString(6, config.getUserStatus());
            pstmt.setString(7, config.getCreatedOn());
            pstmt.setString(8, config.getCreatedBy());
            pstmt.setString(9, config.getName());
            pstmt.setString(10, config.getPriority());
            pstmt.setString(11, config.getStatus());
            pstmt.setString(12, config.getLatestFinishDate());
            pstmt.setString(13, config.getEarliestStartDate());
            pstmt.setString(14, config.getLatestStartDate());
            pstmt.setString(15, config.getEstimatedTime());
            pstmt.setString(16, config.getConfigName());
            pstmt.setBoolean(17, config.isPrivacy());
            pstmt.setString(18, config.getCreatorName());
            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Config could not be saved");
            } else {
                System.out.println("Config saved correctly");
            }
            pool.checkIn(con);

        } catch (SQLException ex) {
            Logger.getLogger(DALHistory.class
                    .getName()).log(
                            Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Config could not be saved");

        }
    }
}
