/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Ticket;
import model.TicketDAO;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author linhph
 */
@Controller
@RequestMapping(value="/ticket")
public class TicketController {
    @RequestMapping(value="/manageTiket", method = RequestMethod.GET)
    public String userTiketPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("user");
        if(us == null)
            return "jsp/index";
        int userid = us.getUserId();
        
        ArrayList<Ticket> listTicket = TicketDAO.getListTicketByUserId(userid);
        
        for(Ticket t: listTicket) {    
            System.out.println(t.getCheckoutTime());
        }
        mm.put("menuitem", "tiketmenu");
        mm.put("listTicket", listTicket);
        
        return "jsp/user/userTicket";
    }
}
