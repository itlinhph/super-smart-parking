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
public class UserDAO {
    
    
    public static boolean checkUserExist(String username) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        String query = "SELECT id FROM user WHERE username =?";
        PreparedStatement st = (PreparedStatement) connect.con.prepareStatement(query);
        st.setString(1,username);
        ResultSet rs = st.executeQuery();
        
        if(rs.next()) {
            return true;
        }
        
        return false;
    }
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
            User user = getUserById(userid);
            return user;
        }
        return null;
    }
    
    //    get user by id from database
    public static User getUserById(int userId) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        User user = new User();
        String query = "Select username, password, status, fullname, email, phone, coin_remain, created From user where id=?";
        PreparedStatement st = (PreparedStatement) connect.con.prepareStatement(query);
        st.setInt(1,userId);
        ResultSet rs = st.executeQuery();
        rs.next();
        user.setUserId(userId) ;
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setStatus(rs.getString("status"));
        user.setFullname(rs.getString("fullname")); 
        user.setEmail(rs.getString("email")); 
        user.setPhone(rs.getString("phone"));
        user.setCoin_remain(rs.getInt("coin_remain")); 
        user.setCreated(rs.getString("created"));
        
        user.setListVehicle(VehicleDAO.getListVehicleByUserid(userId));
        
        return user;
        
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
            
            User user = getUserById(userid);
            
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

    
    public static User getUserByPlate(String plate) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        User user = new User();
        String query = "Select u.id, u.username, u.status, u.fullname, u.coin_remain "
                + "From user u, vehicle v where v.user_id = u.id and v.plate=?";
        PreparedStatement st = (PreparedStatement) connect.con.prepareStatement(query);
        st.setString(1,plate);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            user.setUserId(rs.getInt("id")) ;
            user.setUsername(rs.getString("username"));
            user.setStatus(rs.getString("status"));
            user.setFullname(rs.getString("fullname"));
            user.setCoin_remain(rs.getInt("coin_remain"));
            
        }
        
        return user;
        
    }

    public static ArrayList<User> getListUser() {
        ArrayList<User> listUser = new ArrayList<User>();
        
        try {
            DbConnect connect = new DbConnect();
            String query = 
                "SELECT id, username, status, fullname, email, phone, coin_remain, created "
              + "FROM user order by status desc;";
            
            PreparedStatement statement = connect.con.prepareStatement(query);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("id")) ;
                user.setUsername(rs.getString("username"));
                user.setStatus(rs.getString("status"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setCoin_remain(rs.getInt("coin_remain"));
                user.setCreated(rs.getString("created"));
                listUser.add(user);
            }
            
            connect.con.close();
        }
        catch (Exception e) {
            
            System.out.println("Error getListUser: "+ e.getMessage());
        }
        
        return listUser;
    
    }

    public static boolean setStatusUser(int idUser, String status) {
       
        try {
            DbConnect connect = new DbConnect();
            String query = "UPDATE user SET status = ? WHERE (id = ?)" ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setString(1, status);
            statement.setInt(2, idUser);

            int rs = statement.executeUpdate();
            if(rs >0)
                return true;
            connect.con.close();
        } catch (Exception e) {
            System.out.println("Exeption set status user: "+ e.getMessage());
            
        }
        
        return false; 
  
    }

    public static boolean addMoreCoin(int userid, int coin) {
        try {
            DbConnect connect = new DbConnect();
            String query = "UPDATE user SET coin_remain = (coin_remain + ?) WHERE (id = ?)" ;
            PreparedStatement statement = (PreparedStatement) connect.con.prepareStatement(query);
            statement.setInt(1, coin);
            statement.setInt(2, userid);
            
            int rs = statement.executeUpdate();
            if(rs >0)
                return true;
            connect.con.close();
        } catch (Exception e) {
            System.out.println("Exeption add more coin: "+ e.getMessage());
            
        }
        
        return false; 
    }
}
    