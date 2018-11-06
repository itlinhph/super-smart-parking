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
    
    public static boolean editVehicle(int idvehicle, String plate, int userid, String model, String imgFile, String description) {
        try {
            DbConnect connect = new DbConnect();
            String query = "UPDATE vehicle SET plate = ?, model = ?, img = ?, description =? WHERE (id = ? and user_id = ?)" ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setString(1, plate);
            statement.setString(2, model);
            statement.setString(3, imgFile);
            statement.setString(4, description);
            statement.setInt(5, idvehicle);
            statement.setInt(6, userid);
//            System.out.println(statement);
            int rs = statement.executeUpdate();
            if(rs >0)
                return true;
            connect.con.close();
        } catch (Exception e) {
            System.out.println("Exeption editUserInfor: "+ e.getMessage());
            
        }
        
        return false;
    }
    
    public static boolean addVehicle(int userid, String plate, String model, String description, String image) {
        try {
                DbConnect connect = new DbConnect();
                String query = "Insert into vehicle(plate, user_id, model, img, description) values (?, ?, ?, ?, ?)";
                PreparedStatement statement = connect.con.prepareStatement(query);
                statement.setString(1, plate);
                statement.setInt(2, userid);
                statement.setString(3, model);
                statement.setString(4, image);
                statement.setString(5, description);
                int result = statement.executeUpdate();
                if(result ==1)
                    return true;
            }
            catch (Exception e) {
                System.out.println("SQL Exeption: "+ e.getMessage());
            }
            return false;

    }
    
//    public static void main(String[] args) {
//        ArrayList<Vehicle> listvehicle = getListVehicleByUserid(3);
//        for(Vehicle v: listvehicle) {
//            
//            System.out.println(v.getPlate());
//        }
//    }

   
}
