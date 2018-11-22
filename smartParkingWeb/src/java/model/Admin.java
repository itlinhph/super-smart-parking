/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import otherAddOn.DbConnect;

/**
 *
 * @author linhph
 */
public class Admin implements Serializable {
    private int id;
    private String username;

    
    public Admin() {}
    
    public Admin(String username, String password) {
        try {
            DbConnect connect = new DbConnect();
            String query = "Select id, username From admin where username=? and password = md5(?) and status='working';";
            PreparedStatement st = (PreparedStatement) connect.con.prepareStatement(query);
            st.setString(1,username);
            st.setString(2,password);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                this.id = rs.getInt("id");
                this.username = rs.getString("username");
            }
        } catch (Exception e) {
            System.out.println("Can't get Admin!");
        }
        System.out.println("Admin: "+ this.username );
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}
