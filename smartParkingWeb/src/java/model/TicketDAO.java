/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import otherAddOn.DbConnect;

/**
 *
 * @author linhph
 */
public class TicketDAO {
    
    public static ArrayList<Ticket> getListTicketByUserId(int userid) {
        ArrayList<Ticket> listTicket = new ArrayList<Ticket>();
        
        try {
            DbConnect connect = new DbConnect();
            String query = 
                  "SELECT ticket.id as id, vehicle.plate as plate, park.park_code as parkCode, ticket.ticket_code as ticketCode, "
                + "ticket.status as status, ticket.fee as fee, ticket.checkin_time as checkinTime, ticket.checkout_time as checkoutTime "
                + "FROM ticket, user, park, vehicle "
                + "WHERE vehicle.user_id = user.id and vehicle.id = ticket.vehicle_id and park.id = ticket.park_id "
                + "AND user.id = ? ORDER BY id desc;";
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setInt(1, userid);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Ticket t = new Ticket();
                t.setTicketid(rs.getInt("id"));
                t.setPlate(rs.getString("plate"));
                t.setPark(rs.getString("parkCode"));
                t.setTicketCode(rs.getString("ticketCode"));
                t.setStatus(rs.getString("status"));
                t.setFee(rs.getInt("fee"));
                t.setCheckinTime(rs.getString("checkinTime"));
                t.setCheckoutTime(rs.getString("checkoutTime"));
                listTicket.add(t);
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getListTicketByUserid: "+ e.getMessage());
        }
        
        return listTicket;

    }
    
    
    public static ArrayList<Ticket> getListTicketByParkId(int parkid) {
        ArrayList<Ticket> listTicket = new ArrayList<Ticket>();
        
        try {
            DbConnect connect = new DbConnect();
            String query = 
                "SELECT t.id, v.plate, p.park_code, t.ticket_code, t.status, t.fee, t.checkin_time, t.checkout_time " +
                "FROM ticket t, park p, vehicle v " +
                "WHERE p.id = t.park_id and v.id = t.vehicle_id " +
                "and t.checkin_time > curdate() " +
                "and p.id = ? order by status desc, checkin_time desc ;";
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setInt(1, parkid);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Ticket t = new Ticket();
                t.setTicketid(rs.getInt("id"));
                t.setPlate(rs.getString("plate"));
                t.setPark(rs.getString("park_code"));
                t.setTicketCode(rs.getString("ticket_code"));
                t.setStatus(rs.getString("status"));
                t.setFee(rs.getInt("fee"));
                t.setCheckinTime(rs.getString("checkin_time"));
                t.setCheckoutTime(rs.getString("checkout_time"));
                listTicket.add(t);
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getListTicketByStaffId: "+ e.getMessage());
        }
        
        return listTicket;

    }
    
    
    public static boolean createTicket(int vehicleId, int parkId) {
        
        int ticketCode = (int )(Math.random() *8999  + 1000);
        
        try {
            DbConnect connect = new DbConnect();
            String query = "Insert into ticket(vehicle_id, park_id, ticket_code) values (?, ?, ?)";
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setInt(1, vehicleId);
            statement.setInt(2, parkId);
            statement.setInt(3, ticketCode);
            int result = statement.executeUpdate();
            if(result ==1)
                return true;
        }
        catch (Exception e) {
            System.out.println("SQL Exeption: "+ e.getMessage());
        }
        return false;
    }
    

    public static Ticket getTicketByPlate(String plate) {
        Ticket t = new Ticket();
        try {
            DbConnect connect = new DbConnect();
            String query = 
                "SELECT t.id, t.status, t.checkin_time, t.ticket_code, v.plate " +
                "FROM ticket t, vehicle v where t.vehicle_id = v.id and v.plate = ? and t.status='working' LIMIT 1;";
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setString(1, plate);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                t.setTicketid(rs.getInt("id"));
                t.setStatus(rs.getString("status"));
                t.setCheckinTime(rs.getString("checkin_time"));
                t.setTicketCode(rs.getString("ticket_code"));
                t.setPlate(rs.getString("plate"));
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getTicketByPlate: "+ e.getMessage());
        }
        
        return t;
    }
    
}
