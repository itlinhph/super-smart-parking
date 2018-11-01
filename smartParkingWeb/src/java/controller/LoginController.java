/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@RequestMapping(value="/login") 
public class LoginController {
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public String loginPage( HttpServletRequest request,HttpServletResponse response, ModelMap mm) throws IOException {
        
        try {
            request.setCharacterEncoding("utf-8");
            String action = request.getParameter("action");
            
            int isLogin = 1;
            if(action !=null && action.equals("signup")) {
                isLogin = 0;
            }
            mm.put("isLogin", isLogin);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect(request.getContextPath()+"/index.html");
        }
        
        
        return "jsp/login" ;
    }
    
    @RequestMapping(value="/loginForm", method=RequestMethod.POST)
    public String loginForm( HttpServletRequest request,HttpServletResponse response, ModelMap mm) throws IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
//            System.out.println(username + password);
            User user = UserData.checkValidLogin(username, password);
            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user); 
                response.sendRedirect(request.getContextPath()+"/index.html"); 
            }
            else {
                
                
                mm.put("message", "Login false, wrong username or password!");
            }
            
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect(request.getContextPath()+"/index.html");
        }
        
        return "jsp/login";
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath());

    }
    
    @RequestMapping(value="/registerUser", method=RequestMethod.POST)
    @ResponseBody
    public String registerUser (HttpServletRequest request) {
        
        String message = "";
        try {
            request.setCharacterEncoding("UTF-8");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String fullname = request.getParameter("fullname");
            
            String phone = request.getParameter("phone");
            
            
            if(User.checkUserExist(username)){
                message = "Username exist!";
            }
            else {
                boolean addUserResult = UserData.addUser(username, email, password, fullname, phone);
                if(addUserResult)
                    message = "Register Success!";
                else
                    message = "Register False!";
            }
            
        } catch (Exception e) {
            System.out.println("Exeption: "+ e.getMessage());
            message = e.getMessage();
        }
        return message ;
    }
    
    
    
    
    
    
}
