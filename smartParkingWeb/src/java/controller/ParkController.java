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
import model.Park;
import model.ParkDAO;
import model.Staff;
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
@RequestMapping(value="/park")
public class ParkController {
    
    @RequestMapping(value="/adminParkInfor", method = RequestMethod.GET)
    public String getParkingInfor(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        ArrayList<Park> listPark = ParkDAO.getListParkData();
        mm.put("listPark", listPark);
                        
        return "jsp/admin/parkingInfor" ;
    }
    
    @RequestMapping(value="/adminParkDetail", method = RequestMethod.GET)
    public String getParkingDetail(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        int parkid = Integer.parseInt(request.getParameter("parkid")) ;
        Park park = ParkDAO.getParkById(parkid);
        mm.put("park", park);
        
        ArrayList<Ticket> listTicket = TicketDAO.getListTicketByParkId(park.getId());
        mm.put("listTicket", listTicket);
                        
        return "jsp/admin/parkingDetail" ;
    }
    
    @RequestMapping(value="/userParkInfor", method = RequestMethod.GET)
    public String getParkingForUser(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("user");
        if(us == null)
            return "jsp/index";
        
        ArrayList<Park> listPark = ParkDAO.getListParkData();
        
        mm.put("menuitem", "parkmenu");
        mm.put("listPark", listPark);
        
        return "jsp/user/userParkInfor";
    }
    
    @RequestMapping(value="/staffParkInfor", method = RequestMethod.GET)
    public String getParkingForStaff(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff == null) {
            return "jsp/index";
        }
        Park park = ParkDAO.getParkByStaffId(staff.getId());
        mm.put("park", park);
        
        ArrayList<Ticket> listTicket = TicketDAO.getListTicketByParkId(park.getId());
        mm.put("listTicket", listTicket);
                
        return "jsp/staff/staffPark" ;
    }
    
}
