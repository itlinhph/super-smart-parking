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
    

    public static User editUserInfor(int userid, String email, String fullname, String phone) {
//        System.out.println(fullname+ userid+ email+ phone);
        try {
            DbConnect connect = new DbConnect();
            String query = "UPDATE user SET fullname = ?, email = ?, phone = ? WHERE id = ?" ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setString(1, fullname);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setInt(4, userid);
//            System.out.println(statement);
            int rs = statement.executeUpdate();
            
            User user = new User(userid);
            
            connect.con.close();
            return user;
        } catch (Exception e) {
            System.out.println("Exeption editUserInfor: "+ e.getMessage());
            return null;
        }
        
    }

    public static boolean editPassword(int userid, String oldPass, String newPass) {
        
        try {
            
            DbConnect connect = new DbConnect();
            String query = "UPDATE user SET password = md5(?) WHERE (id = ? and password = md5(?))" ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setString(1, newPass);
            statement.setInt(2, userid);
            statement.setString(3, oldPass);
//            System.out.println(statement);
            int rs = statement.executeUpdate();
            if(rs > 0) {
                System.out.println(rs);
                connect.con.close();
                return true;
            }
            
        } catch (Exception e) {
            System.out.println("Exeption editPassword: "+ e.getMessage());
        }
        return false;
    }

}
    