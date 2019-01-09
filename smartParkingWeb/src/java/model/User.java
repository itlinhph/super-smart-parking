/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author linhph
 */
public class User implements Serializable {
    private int userId;
    private String username;
    private String password;
    private String status;
    private String fullname;
    private String email;
    private String phone;
    private int coin_remain;
    private String created;
    private ArrayList<Vehicle> listVehicle;

    public User() {

    }

    public User(String username, String email, String password, String fullname, String phone) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public ArrayList<Vehicle> getListVehicle() {
        return listVehicle;
    }

    public void setListVehicle(ArrayList<Vehicle> listVehicle) {
        this.listVehicle = listVehicle;
    }

}
