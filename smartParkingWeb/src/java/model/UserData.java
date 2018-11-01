/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import otherAddOn.DbConnect;

/**
 *
 * @author linhph
 */
public class UserData {
    //    add new user
    public static boolean addUser(String username, String email, String password, String fullname, String phone){
        User user = new User(username, email, password, fullname, phone) ;
        
        try {
            DbConnect connect = new DbConnect();
            String query = "Insert into user(username, email, password, fullname, phone) values (?, ?, md5(?), ?, ?)";
            PreparedStatement statement = connect.con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, fullname);
            statement.setString(5, phone);
            int result = statement.executeUpdate();
            if(result ==1)
                return true;
        }
        catch (Exception e) {
            System.out.println("SQL Exeption: "+ e.getMessage());
        }
        return false;
    }
    
    public static User checkValidLogin(String username, String password) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        String query = "SELECT id from user WHERE username =? and password = md5(?)" ;
        PreparedStatement statement = connect.con.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            int userid = rs.getInt("id");
            User user = new User(userid);
            return user;
        }
        return null;
    }
    
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        try {
//            User user = checkValidLogin("linh phan", "111");
//            System.out.println("Username: "+ user.getFullname());
//            
//        } catch (Exception e) {
//            System.out.println("Error: "+ e.getMessage());
//        }
//        
//    }
    
}

    
