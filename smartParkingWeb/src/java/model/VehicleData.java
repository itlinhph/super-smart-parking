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
            String query = "Select id, plate, model, img, status, description from vehicle where user_id = ? and status !='deactive'" ;
            
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
            String query = "";
            PreparedStatement statement = null;
            if (imgFile.isEmpty()) {
                query = "UPDATE vehicle SET plate = ?, model = ?, description =? WHERE (id = ? and user_id = ?)" ;
                statement = (PreparedStatement) connect.con.prepareStatement(query);
                statement.setString(1, plate);
                statement.setString(2, model);
                statement.setString(3, description);
                statement.setInt(4, idvehicle);
                statement.setInt(5, userid);
            } else {
                imgFile = "resources/images/" + imgFile;
                query = "UPDATE vehicle SET plate = ?, model = ?, description =?, img = ? WHERE (id = ? and user_id = ?)" ;
                statement = (PreparedStatement) connect.con.prepareStatement(query);
                statement.setString(1, plate);
                statement.setString(2, model);
                statement.setString(3, description);
                statement.setString(4,imgFile );
                statement.setInt(5, idvehicle);
                statement.setInt(6, userid);
            }

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
                if (image.isEmpty())
                    image = "resources/images/defaultImage.jpg";
                else 
                    image = "resources/images/" + image;
                String query = "Insert into vehicle(plate, user_id, model, img, description) values (?, ?, ?, ?, ?)";
                PreparedStatement statement = connect.con.prepareStatement(query);
                statement.setString(1, plate);
                statement.setInt(2, userid);
                statement.setString(3, model);
                statement.setString(4, image);
                statement.setString(5, description);
                int result = statement.executeUpdate();
                System.out.println(statement);
                if(result ==1)
                    return true;
            }
            catch (Exception e) {
                System.out.println("SQL Exeption: "+ e.getMessage());
            }
            return false;

    }

    public static boolean deactiveVehicle(int userid, int idVehicle) {
        try {
            DbConnect connect = new DbConnect();
            String query = "UPDATE vehicle SET status ='deactive' WHERE (id = ? and user_id = ?)" ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setInt(1, idVehicle);
            statement.setInt(2, userid);

//            System.out.println(statement);
            int rs = statement.executeUpdate();
            if(rs >0)
                return true;
            connect.con.close();
        } catch (Exception e) {
            System.out.println("Exeption deactive vehicle: "+ e.getMessage());
            
        }
        
        return false;
    }

    
    public static Vehicle getVehicleByPlate(String plate) {
    
        Vehicle v = new Vehicle();
        try {
            DbConnect connect = new DbConnect();
            String query = "Select id, plate, model, img, status, description from vehicle where plate = ? ;" ;
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setString(1, plate);
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                v.setId(rs.getInt("id"));
                v.setPlate(rs.getString("plate"));
                v.setModel(rs.getString("model"));
                v.setImg(rs.getString("img"));
                v.setStatus(rs.getString("status"));
                v.setDescription(rs.getNString("description"));
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getVehicleByPlate: "+ e.getMessage());
        }
        
        return v;
    }

    public static boolean checkoutVehicle(int ticketid) {
        
        try {
            DbConnect connect = new DbConnect();
            String query = "UPDATE ticket t, vehicle v, user u " +
            "SET t.status = 'expired' , u.coin_remain = u.coin_remain - 1, t.checkout_time = current_time() " +
            "WHERE v.user_id = u.id and t.vehicle_id = v.id and t.id = ? ; " ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setInt(1, ticketid);
            int rs = statement.executeUpdate();
            if(rs >0)
                return true;
            connect.con.close();
        } catch (Exception e) {
            System.out.println("Exeption deactive vehicle: "+ e.getMessage());
            
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
