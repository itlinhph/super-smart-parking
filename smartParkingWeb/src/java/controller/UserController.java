/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.User;
import model.UserData;
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
            System.out.println("Exeption: "+ e.getMessage());
        }
        
        return message;
    }
}
