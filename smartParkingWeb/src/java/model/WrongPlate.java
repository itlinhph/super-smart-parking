/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author linkpp
 */
public class WrongPlate {
    private int id;
    private String detectedPlate;
    private String fixedPlate;
    private String img;
    private String status;
    private int parkId;
    private String checkinTime;

    public WrongPlate() {
    }

    
    public String getFixedPlate() {
        return fixedPlate;
    }

    public void setFixedPlate(String fixedPlate) {
        this.fixedPlate = fixedPlate;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetectedPlate() {
        return detectedPlate;
    }

    public void setDetectedPlate(String detectedPlate) {
        this.detectedPlate = detectedPlate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }
    

}
