/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import otherAddOn.DbConnect;

/**
 *
 * @author linhph
 */
public class ReportDAO {
    
    public static Report getReportByTicketId(int ticketId, int userId) {
        
        Report report = new Report();
        try {
            DbConnect connect = new DbConnect();
            String query = "Select r.id, r.type, r.ticket_id, r.description, r.status, r.admin_note, r.created, r.processed_time, u.email "
                    + "FROM report r, ticket t, vehicle v, user u  "
                    + "WHERE r.ticket_id = t.id AND t.vehicle_id = v.id AND v.user_id = u.id AND t.id = ? and u.id = ?" ;
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setInt(1, ticketId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                report.setId(rs.getInt("id"));
                report.setType(rs.getString("type"));
                report.setTicketId(rs.getInt("ticket_id"));
                report.setDescription(rs.getString("description"));
                report.setStatus(rs.getString("status"));
                report.setAdminNote(rs.getString("admin_note"));
                report.setCreated(rs.getString("created"));
                report.setProcessedTime(rs.getString("processed_time"));
                report.setEmailUser(rs.getString("email"));
               
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getReportByTicketId: "+ e.getMessage());
        }
        
        return report;
    }

    public static boolean createReport(String type, int ticketId, String description) {
        try {
            DbConnect connect = new DbConnect();
            String query = "Insert into report(type, ticket_id, description) values (?, ?, ?)";
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setString(1, type);
            statement.setInt(2, ticketId);
            statement.setString(3, description);
            int result = statement.executeUpdate();
            if(result ==1)
                return true;
        }
        catch (Exception e) {
            System.out.println("SQL Exeption: "+ e.getMessage());
        }
        return false;
    }
    
}
