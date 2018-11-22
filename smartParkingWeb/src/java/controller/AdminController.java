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
import model.ParkData;
import model.Ticket;
import model.TicketData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author linhph
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {
    @RequestMapping(value="/parking", method = RequestMethod.GET)
    public String getParkingInfo(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        ArrayList<Park> listPark = ParkData.getListParkData() ;
        mm.put("listPark", listPark);
                        
        return "jsp/admin/parkingInfor" ;
    }
    
    
    
}
