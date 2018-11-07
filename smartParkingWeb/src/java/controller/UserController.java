/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.User;
import model.UserData;
import model.Vehicle;
import model.VehicleData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author linhph
 */

@Controller
@RequestMapping(value="/user")
public class UserController {
    
    @RequestMapping(value="/userInfor", method = RequestMethod.GET)
    public String userInforPage(ModelMap mm) {
        mm.put("menuitem", "userInformenu");
        return "jsp/user/userInfor" ;
    }
    
    @RequestMapping(value="/vehicle", method = RequestMethod.GET)
    public String userVehiclePage(ModelMap mm) {
        mm.put("menuitem", "vehiclemenu");
        return "jsp/user/userVehicle";
    }
    
    
    @RequestMapping(value="/editProfile", method = RequestMethod.POST)
    @ResponseBody
    public String editProfile(HttpServletRequest request) {
        
        String message = "Edit profile false!";
        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("user");
            int userid = us.getUserId();
            request.setCharacterEncoding("UTF-8");
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            
            if(email.equals(us.getEmail()) && fullname.equals(us.getFullname()) && phone.equals(us.getPhone()))
                return "Nothing to change!";
                
            
            User userNew = UserData.editUserInfor(userid, email, fullname, phone);
            if(userNew != null) {
                
                message = "Edit profile success!";
                session.setAttribute("user", userNew);
                
            }
            
        } catch (Exception e) {
            message = "Edit profile false: "+ e.getMessage();
            System.out.println(message);
        }
        return message;
    }
    
    
    @RequestMapping(value="/changePass", method=RequestMethod.POST)
    @ResponseBody
    public String changePass(HttpServletRequest request) {
        String message = "Your old password is wrong or something else!";
        try {
            
            request.setCharacterEncoding("UTF-8");
            String oldPass = request.getParameter("oldPass");
            String newPass = request.getParameter("newPass");
            String confirmPass = request.getParameter("confirmPass");
            if(!newPass.equals(confirmPass)) {
                return "Your confirm password isn't equal with new pass!" ;
            }
            else if(newPass.equals(oldPass)) {
                return "Nothing to change!";
            }
            
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("user");
            int userid = us.getUserId();
            boolean result = UserData.editPassword(userid, oldPass, newPass);
            if(result)
                message = "Change Password success!";
        } catch (Exception e) {
            System.out.println("Exeption changePass: "+ e.getMessage());
        }
        
        return message;
    }
    
    @RequestMapping(value="/editVehicle", method=RequestMethod.POST)
    public String editVehicle(HttpServletRequest request, ModelMap mm) {
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
            int idvehicle = Integer.parseInt(request.getParameter("idvehicle")) ;
            
            boolean result = VehicleData.editVehicle(idvehicle, plate, userid, model, imgFile, description);
            if(result) {
                mm.put("message","Edit vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleData.getListVehicleByUserid(userid);
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
            
            boolean result = VehicleData.addVehicle(userid, plate, model, description, imgFile);
            if(result) {
                mm.put("message","Add vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleData.getListVehicleByUserid(userid);
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
            
            boolean result = VehicleData.deactiveVehicle(userid, idVehicle);
            if(result) {
                mm.put("message","Deactive vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleData.getListVehicleByUserid(userid);
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
