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
                + "ticket.status as status, ticket.checkin_time as checkinTime, ticket.checkout_time as checkoutTime "
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
    
//    public static void main(String[] args) {
//        ArrayList<Ticket> l = getListTicketByUserId(3);
//        for (Ticket t: l)
//            System.out.println(t.getCheckoutTime());
//    }
    
}
