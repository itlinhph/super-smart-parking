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
public class WrongPlateData {
    public static ArrayList<WrongPlate> getListWrongPlateByParkId(int parkid) {
        ArrayList<WrongPlate> listWrongPlate = new ArrayList<WrongPlate>();
        
        try {
            DbConnect connect = new DbConnect();
            String query = "SELECT id, img, detect_result, status, park_id, checkin_time FROM smart_parking.wrong_plate where park_id = ? and status= 'pending';" ;
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setInt(1, parkid);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                WrongPlate wp = new WrongPlate();
                wp.setId(rs.getInt("id"));
                wp.setImg(rs.getString("img") );
                wp.setDetectedPlate(rs.getString("detect_result") );
                wp.setStatus(rs.getString("status") );
                wp.setParkId(rs.getInt("park_id") );
                wp.setCheckinTime(rs.getString("checkin_time") );
                listWrongPlate.add(wp);
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getListWrongPlateByStaffId: "+ e.getMessage());
        }
        
        return listWrongPlate;

    }
    
    public static boolean editFixPlate(String plate, int wrongPlateId, int parkid) {
        
        
        try {
            DbConnect connect = new DbConnect();
            
            String query = "SELECT id FROM vehicle WHERE plate= ? and status = 'working' ";
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setString(1, plate);
            ResultSet rs = statement.executeQuery();
            
            if(!rs.next())
                return false;
            int vehicleId = rs.getInt("id");
            boolean resultCreateTicket = TicketData.createTicket(vehicleId, parkid);
            if(!resultCreateTicket)
                return false;
                    
            query = "UPDATE wrong_plate SET fixed_plate = ?, status ='fixed' WHERE id = ? and park_id = ?" ;
            statement = connect.con.prepareStatement(query);
            statement.setString(1, plate);
            statement.setInt(2, wrongPlateId);
            statement.setInt(3, parkid);
            int result = statement.executeUpdate();
            if(result > 0) {
                connect.con.close();
                return true;
            }
            else {
                connect.con.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exeption editFixPlate: "+ e.getMessage());
            
        }
        
        return false;
    }
}
