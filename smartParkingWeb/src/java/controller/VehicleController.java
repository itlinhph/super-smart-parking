/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Admin;
import model.User;
import model.Vehicle;
import model.VehicleDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author linhph
 */
@Controller
@RequestMapping(value="/vehicle")
public class VehicleController {
    
    @RequestMapping(value="/userVehiclePage", method = RequestMethod.GET)
    public String userVehiclePage(ModelMap mm) {
        mm.put("menuitem", "vehiclemenu");
        return "jsp/user/userVehicle";
    }
    
    @RequestMapping(value="/adVerifyVehicle", method = RequestMethod.GET)
    public String getVerifyVehiclePage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        ArrayList<Vehicle> listVehicle = VehicleDAO.getListPendingVehicle();
        mm.put("listVehicle", listVehicle);
                        
        return "jsp/admin/verifyVehicle" ;
    }
    
    @RequestMapping(value="/adVerifyVehicleAction", method=RequestMethod.POST)
    public String verifyVehicleAction(HttpServletRequest request, ModelMap mm) {
        
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            String model = request.getParameter("model");
            String description = request.getParameter("description");
            int idvehicle = Integer.parseInt(request.getParameter("idvehicle")) ;
            
            boolean result = VehicleDAO.editVehicle(idvehicle, plate, model, description);
            if(result) {
                mm.put("message","Verify vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleDAO.getListPendingVehicle();
                session.setAttribute("listVehicle", listVehicle);
                
            }
            else
                mm.put("message", "Verify vehicle false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        return "jsp/admin/verifyVehicle";
    }
    
    @RequestMapping(value="/adRejectVehicle", method=RequestMethod.POST)
    public String rejectVehicle(HttpServletRequest request, ModelMap mm) {
        
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            int idVehicle = Integer.parseInt(request.getParameter("idReject")) ; 
            
            boolean result = VehicleDAO.rejectVehicle(idVehicle);
            if(result) {
                mm.put("message","Reject vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleDAO.getListPendingVehicle();
                mm.put("listVehicle", listVehicle);
            }
            else
                mm.put("message", "Reject vehicle false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        return "jsp/admin/verifyVehicle";
    }
    
    @RequestMapping(value="/editVehicle", method=RequestMethod.POST)
    public String editVehicle(HttpServletRequest request, ModelMap mm) {
//        String messages = "Edit Vehicle false!";
        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("user");
            if(us == null)
                return "jsp/index";
            
            int userid = us.getUserId();
            
            request.setCharacterEncoding("UTF-8");
            String imgFile = request.getParameter("imgFile");
            String plate = request.getParameter("plate");
            String model = request.getParameter("model");
            String description = request.getParameter("description");
            int idvehicle = Integer.parseInt(request.getParameter("idvehicle")) ;
            
            boolean result = VehicleDAO.editVehicle(idvehicle, plate, userid, model, imgFile, description);
            if(result) {
                mm.put("message","Edit vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleDAO.getListVehicleByUserid(userid);
                us.setListVehicle(listVehicle);
                session.setAttribute("user", us);
                
            }
            else
                mm.put("message", "Edit vehicle false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        return "jsp/user/userVehicle";
    }
    
    @RequestMapping(value="/addVehicle", method=RequestMethod.POST)
    public String addNewVehicle(HttpServletRequest request, ModelMap mm) {
        String messages = "Edit Vehicle false!";
        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("user");
            if(us == null)
                return "jsp/index";
            
            int userid = us.getUserId();
            
            request.setCharacterEncoding("UTF-8");
            String imgFile = request.getParameter("imgFile");
            String plate = request.getParameter("plate");
            String model = request.getParameter("model");
            String description = request.getParameter("description");
            
            boolean result = VehicleDAO.addVehicle(userid, plate, model, description, imgFile);
            if(result) {
                mm.put("message","Add vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleDAO.getListVehicleByUserid(userid);
                us.setListVehicle(listVehicle);
                session.setAttribute("user", us);
                
            }
            else
                mm.put("message", "Add vehicle false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        return "jsp/user/userVehicle";
    }
    
    @RequestMapping(value="/deactiveVehicle", method=RequestMethod.POST)
    public String deactiveVehicle(HttpServletRequest request, ModelMap mm) {
        String messages = "Deactive false!";
        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("user");
            if(us == null)
                return "jsp/index";
            
            int userid = us.getUserId();
            
            request.setCharacterEncoding("UTF-8");
            int idVehicle = Integer.parseInt(request.getParameter("idDeactive")) ; 
            
            boolean result = VehicleDAO.deactiveVehicle(userid, idVehicle);
            if(result) {
                mm.put("message","Deactive vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleDAO.getListVehicleByUserid(userid);
                us.setListVehicle(listVehicle);
                session.setAttribute("user", us);
                
            }
            else
                mm.put("message", "Deactive vehicle false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        return "jsp/user/userVehicle";
    }
    
    
}
