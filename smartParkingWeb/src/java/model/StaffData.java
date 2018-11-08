/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        Staff s = getStaffBySId(1);
//        System.out.println(s.getStaffName());
//    }
}
