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
public class VehicleData {
    public static ArrayList<Vehicle> getListVehicleByUserid(int userid) {
        ArrayList<Vehicle> listVehicle = new ArrayList<Vehicle>();
        
        try {
            DbConnect connect = new DbConnect();
            String query = "Select id, plate, model, img, status, description from vehicle where user_id = ?" ;
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setInt(1, userid);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setPlate(rs.getString("plate"));
                v.setModel(rs.getString("model"));
                v.setImg(rs.getString("img"));
                v.setStatus(rs.getString("status"));
                v.setDescription(rs.getNString("description"));
                listVehicle.add(v);
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getListVehicleByUserid: "+ e.getMessage());
        }
        
        return listVehicle;
        
    }
    
    
//    public static void main(String[] args) {
//        ArrayList<Vehicle> listvehicle = getListVehicleByUserid(3);
//        for(Vehicle v: listvehicle) {
//            
//            System.out.println(v.getPlate());
//        }
//    }
}
