/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import otherAddOn.DbConnect;

/**
 *
 * @author linhph
 */
public class User implements Serializable {
    private int userId;
    private String username;
    private String password;
    private int status;
    private String fullname;
    private String email;
    private String phone;
    private int coin_remain;
    private String note;
    private String created;
    
    
    public User() {
        
    }

    public User(String username, String password, String fullname, String email, String phone) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
    }
    
//    get user by id from database
    public User(int userId) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        String query = "Select username, password, status, fullname, email, phone, coin_remain, note, created From user where id="+ userId;
        ResultSet rs = connect.st.executeQuery(query) ;
        rs.next();
        this.userId = userId;
        this.username = rs.getString("username");
        this.password = rs.getString("password");
        this.status = rs.getInt("status");
        this.fullname = rs.getString("fullname");
        this.email = rs.getString("email");
        this.phone = rs.getString("phone");
        this.coin_remain = rs.getInt("coin_remain");
        this.note = rs.getString("note");
        this.created = rs.getString("created");
        
    }
    
    public static boolean checkUserExist(String username) throws SQLException, ClassNotFoundException {
        DbConnect connect = new DbConnect();
        String sql = "SELECT id FROM user WHERE username = '" + username + "'";
        
        ResultSet rs = connect.st.executeQuery(sql);
        if(rs.next()) {
            return true;
        }
        
        return false;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCoin_remain() {
        return coin_remain;
    }

    public void setCoin_remain(int coin_remain) {
        this.coin_remain = coin_remain;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
////        User user = new User(1) ;
//        boolean exist = checkUserExist("linhph");
//        System.out.println(exist);
//    }  
}
