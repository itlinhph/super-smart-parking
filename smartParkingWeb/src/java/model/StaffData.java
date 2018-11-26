/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import otherAddOn.DbConnect;

/**
 *
 * @author linhph
 */
public class StaffData {
    
    public static Staff checkValidLogin(String staffCode, String password) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        String query = "SELECT id from staff WHERE staff_code =? and password = md5(?)" ;
        PreparedStatement statement = connect.con.prepareStatement(query);
        statement.setString(1, staffCode);
        statement.setString(2, password);
        
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            int sid = rs.getInt("id");
            Staff staff = getStaffBySId(sid);
            return staff;
        }
        return null;
    }
    
    public static Staff getStaffBySId(int sid) throws SQLException, ClassNotFoundException {
        Staff s = new Staff();
        DbConnect connect = new DbConnect();
        String query = "Select id, staff_code, password, staff_name, park_id, status, created From staff where id=?";
        PreparedStatement st = (PreparedStatement) connect.con.prepareStatement(query);
        st.setInt(1, sid);
        ResultSet rs = st.executeQuery();
        s.setId(sid);
        rs.next();
        s.setStaffCode(rs.getString("staff_code"));
        s.setPassword(rs.getString("password"));
        s.setStaffName(rs.getString("staff_name"));
        s.setParkid(rs.getInt("park_id"));
        s.setStatus(rs.getString("status"));
        s.setCreated(rs.getString("created"));
        
        return s;
    }
    
    public static ArrayList<Staff> getListStaff() {
       
        ArrayList<Staff> listStaff = new ArrayList<Staff>();
        
        try {
            DbConnect connect = new DbConnect();
            String query = 
                "SELECT s.id, s.staff_code, s.staff_name, p.park_code, s.status, s.created "
              + "FROM staff s, park p WHERE s.park_id = p.id order by status desc;";
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Staff s = new Staff();
                s.setId(rs.getInt("id"));
                s.setStaffCode(rs.getString("staff_code"));
                s.setStaffName(rs.getString("staff_name"));
                s.setParkCode(rs.getString("park_code"));
                s.setStatus(rs.getString("status"));
                s.setCreated(rs.getString("created"));
                listStaff.add(s);
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getListStaff: "+ e.getMessage());
        }
        
        return listStaff;
    }
    
    public static boolean addStaff(String scode, String name, int parkid) {
        
        try {
            DbConnect connect = new DbConnect();
            String query = "Insert into staff(staff_code, password, staff_name, park_id) values (?, md5(?), ?, ?);";
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setString(1, scode);
            statement.setString(2, scode);
            statement.setString(3, name);
            statement.setInt(4, parkid);
            int result = statement.executeUpdate();
            if(result ==1)
                return true;
        }
        catch (Exception e) {
            System.out.println("SQL Exeption: "+ e.getMessage());
        }

        return false;
    }
    
    public static boolean checkStaffCode(String scode) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        String query = "SELECT id FROM staff WHERE staff_code =?";
        PreparedStatement st = (PreparedStatement) connect.con.prepareStatement(query);
        st.setString(1,scode);
        ResultSet rs = st.executeQuery();
        
        if(rs.next()) {
            return true;
        }
        
        return false;
    }
    
    public static boolean deactiveStaff(int idStaff) {
        
        try {
            DbConnect connect = new DbConnect();
            String query = "UPDATE staff SET status ='deactive' WHERE (id = ?)" ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setInt(1, idStaff);

//            System.out.println(statement);
            int rs = statement.executeUpdate();
            if(rs >0)
                return true;
            connect.con.close();
        } catch (Exception e) {
            System.out.println("Exeption deactive staff: "+ e.getMessage());
            
        }
        
        return false;
    }
    
    
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        Staff s = getStaffBySId(1);
//        System.out.println(s.getStaffName());
//    }



 
}
