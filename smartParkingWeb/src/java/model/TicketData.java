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
public class TicketData {
    
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
    
    
    public static ArrayList<Ticket> getListTicketByStaffId(int staffid) {
        ArrayList<Ticket> listTicket = new ArrayList<Ticket>();
        
        try {
            DbConnect connect = new DbConnect();
            String query = 
                "SELECT t.id, v.plate, p.park_code, t.ticket_code, t.status, t.fee, t.checkin_time, t.checkout_time " +
                "FROM staff s, ticket t, park p, vehicle v " +
                "WHERE s.park_id = p.id and p.id = t.park_id and v.id = t.vehicle_id " +
                "and t.checkin_time > curdate() " +
                "and s.id = ? order by status desc, checkin_time desc ;";
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setInt(1, staffid);
            
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
    
//    public static void main(String[] args) {
//        ArrayList<Ticket> l = getListTicketByStaffId(1);
//        for (Ticket t: l)
//            System.out.println(t.getCheckoutTime());
//    }
    
}
