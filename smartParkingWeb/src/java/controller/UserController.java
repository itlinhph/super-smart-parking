/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Admin;
import model.User;
import model.UserDAO;
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
    public String userInforPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("user");
        if(us == null)
            return "jsp/index";
        mm.put("menuitem", "userInformenu");
        return "jsp/user/userInfor" ;
    }
    
    @RequestMapping(value="/buyCoin", method = RequestMethod.GET)
    public String buyCoinPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("user");
        if(us == null)
            return "jsp/index";
        mm.put("menuitem", "buyCoinMenu");
        return "jsp/user/buyCoin" ;
    }
    
    @RequestMapping(value="/adManageUser", method = RequestMethod.GET)
    public String getManageUserPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        
        ArrayList<User> listUser = UserDAO.getListUser();
        mm.put("listUser", listUser);          
        return "jsp/admin/manageUser" ;
    }
    
    @RequestMapping(value="/adSetStatusUser", method=RequestMethod.POST)
    public String setStatusUser(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
         
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            int idUser = Integer.parseInt(request.getParameter("idUser")) ; 
            String status = request.getParameter("status");
            
            boolean result = UserDAO.setStatusUser(idUser, status);
            if(result)
                response.sendRedirect(request.getContextPath()+"/user/adManageUser");
            
            else {
                mm.put("message", "Action with this user false!");
                ArrayList<User> listUser = UserDAO.getListUser();
                mm.put("listUser", listUser);
                
            }
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        
        return "jsp/admin/manageUser";
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
                
            
            User userNew = UserDAO.editUserInfor(userid, email, fullname, phone);
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
            boolean result = UserDAO.editPassword(userid, oldPass, newPass);
            if(result)
                message = "Change Password success!";
        } catch (Exception e) {
            System.out.println("Exeption changePass: "+ e.getMessage());
        }
        
        return message;
    }
    
    @RequestMapping(value="/buyCoinAction", method=RequestMethod.POST)
    @ResponseBody
    public String buyCoinAction(HttpServletRequest request) {
        String message = "Buy coin fail, please try again!";
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user == null) {
                return "jsp/index";
            }
            request.setCharacterEncoding("UTF-8");
            int coin = Integer.parseInt(request.getParameter("coin")) ;
            
            int userid = user.getUserId();
            boolean result = UserDAO.addMoreCoin(userid, coin);
            if(result)
                message = "Buy Coin success!";
        } catch (Exception e) {
            System.out.println("Exeption buy coin: "+ e.getMessage());
        }
        
        return message;
    }

}
