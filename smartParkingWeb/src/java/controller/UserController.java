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
    
    
    @RequestMapping(value="/editProfile", method = RequestMethod.POST)
    @ResponseBody
    public String editProfile(HttpServletRequest request) {
        
        String message = "Edit profile false!";
        try {
            HttpSession session = request.getSession();
            User userSession = (User) session.getAttribute("user");
            int userid = userSession.getUserId();
            request.setCharacterEncoding("UTF-8");
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            
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
}
