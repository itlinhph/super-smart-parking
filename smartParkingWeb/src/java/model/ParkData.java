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
 * @author linkpp
 */
public class ParkData {
    public static ArrayList<Park> getListParkData() {
        
        ArrayList<Park> listPark = new ArrayList<Park>();
         try {
            DbConnect connect = new DbConnect();
            String query = 
                  "SELECT p.id, p.park_code, p.park_name, p.total_slots, count(if(t.status='working', 1, NULL)) as usingSlot, p.description "
                + "FROM park as p, ticket as t where p.id = t.park_id group by p.id ;";
            
            PreparedStatement statement = connect.con.prepareStatement(query);   
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Park p = new Park();
                p.setId(rs.getInt("id"));
                p.setParkCode(rs.getString("park_code"));
                p.setParkName(rs.getString("park_name"));
                p.setTotalSlot(rs.getInt("total_slots"));
                p.setUsingSlot(rs.getInt("usingSlot"));
                p.setDescription(rs.getString("description"));
                listPark.add(p);
            }
            connect.con.close();
            return listPark;
        }
        catch (Exception e) {
            
            System.out.println("Error getListPark: "+ e.getMessage());
        }
        
        return listPark;
    }
    
}
