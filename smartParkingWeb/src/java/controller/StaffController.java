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
import model.User;
import model.UserData;
import model.Vehicle;
import model.VehicleData;
import model.WrongPlate;
import model.WrongPlateData;
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
    public String fixWrongPlatePage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff == null) {
            return "jsp/index";
        }
        
        ArrayList<WrongPlate> listWrongPlate = WrongPlateData.getListWrongPlateByParkId(staff.getParkid());
        mm.put("listWrongPlate",listWrongPlate);
        
        return "jsp/staff/fixWrongPlate" ;
    }
    
    @RequestMapping(value="/checkinout", method=RequestMethod.GET)
    public String checkoutInOutPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff == null) {
            return "jsp/index";
        }
        
        
        return "jsp/staff/checkinout";
    }
    
    
    @RequestMapping(value="/checkoutVehicle", method = RequestMethod.POST) 
    public String checkoutVehicle(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff == null) {
            return "jsp/index";
        }
        
        try {
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            int ticketId = Integer.parseInt(request.getParameter("ticketid")) ;
            int parkid = staff.getParkid() ;
            
            boolean checkoutResult = VehicleData.checkoutVehicle(ticketId);

        } catch (Exception e) {
            System.out.println("Exeption checkoutVehicle: "+ e.getMessage());
        }
        
        return "jsp/staff/checkoutAction" ;
    }
    
    
    @RequestMapping(value="/editWrongPlate", method = RequestMethod.POST)
    public String editWrongPlate(HttpServletRequest request, ModelMap mm) {
        
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff == null) {
            return "jsp/index";
        }
        
        try {
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            String plate_id = request.getParameter("plate_id");
            int plateId = Integer.parseInt(plate_id);
            int parkid = staff.getParkid() ;
            
            boolean editFixedPlateResult = WrongPlateData.editFixPlate(plate, plateId, parkid);

        } catch (Exception e) {
            System.out.println("Exeption editWrongPlate: "+ e.getMessage());
        }
        mm.put("script", "window.location = 'fixplate';") ;
        return "jsp/staff/fixWrongPlate" ;
    }
    
    
    @RequestMapping(value="/checkoutAction", method= RequestMethod.GET)
    public String checkoutAction(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff == null) {
            return "jsp/index";
        }
        try {
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            String img = request.getParameter("img");

            Ticket t = TicketData.getTicketByPlate(plate);
            Vehicle v = VehicleData.getVehicleByPlate(plate);
            User u = UserData.getUserByPlate(plate);
            
            mm.put("vehicle", v);
            mm.put("ticket", t);
            mm.put("user", u);
            mm.put("checkoutImg", img);
            
            
        } catch (Exception e) {
            System.out.println("Exeption checkoutAction: "+ e.getMessage());
        }
        
        return "jsp/staff/checkoutAction";
    }
}
