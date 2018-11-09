/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Park;
import model.ParkData;
import model.Staff;
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
@RequestMapping(value="/staff")
public class StaffController {
    
    @RequestMapping(value="/parking", method = RequestMethod.GET)
    public String userInforPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff == null) {
            return "jsp/index";
        }
        Park park = ParkData.getParkByStaffId(staff.getId());
        mm.put("park", park);
        
        ArrayList<Ticket> listTicket = TicketData.getListTicketByStaffId(staff.getId());
        mm.put("listTicket", listTicket);
                
        return "jsp/staff/staffPark" ;
    }
    
    @RequestMapping(value="/fixplate", method = RequestMethod.GET) 
    public String fixWrongPlarePage(HttpServletRequest request, ModelMap mm) {
        
        
        return "jsp/staff/fixWrongPlate" ;
    }
    
}
