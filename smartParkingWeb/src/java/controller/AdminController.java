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
import model.Staff;
import model.StaffData;
import model.Ticket;
import model.TicketData;
import model.User;
import model.UserData;
import model.Vehicle;
import model.VehicleData;
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
        ArrayList<Park> listPark = ParkData.getListParkData();
        mm.put("listPark", listPark);
                        
        return "jsp/admin/parkingInfor" ;
    }

    
    @RequestMapping(value="/parkingDetail", method = RequestMethod.GET)
    public String getParkingDetail(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        int parkid = Integer.parseInt(request.getParameter("parkid")) ;
        Park park = ParkData.getParkById(parkid);
        mm.put("park", park);
        
        ArrayList<Ticket> listTicket = TicketData.getListTicketByParkId(park.getId());
        mm.put("listTicket", listTicket);
                        
        return "jsp/admin/parkingDetail" ;
    }
    
    @RequestMapping(value="/verifyVehicle", method = RequestMethod.GET)
    public String getVerifyVehiclePage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        ArrayList<Vehicle> listVehicle = VehicleData.getListPendingVehicle();
        mm.put("listVehicle", listVehicle);
                        
        return "jsp/admin/verifyVehicle" ;
    }
    
    @RequestMapping(value="/manageStaff", method = RequestMethod.GET)
    public String getManageStaffPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        ArrayList<Park> listPark = ParkData.getListParkData();
        ArrayList<Staff> listStaff = StaffData.getListStaff();
        mm.put("listStaff", listStaff);          
        mm.put("listPark", listPark);          
        return "jsp/admin/manageStaff" ;
    }
    
    
    @RequestMapping(value="/manageUser", method = RequestMethod.GET)
    public String getManageUserPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        
        ArrayList<User> listUser = UserData.getListUser();
        mm.put("listUser", listUser);          
        return "jsp/admin/manageUser" ;
    }
    
    @RequestMapping(value="/report", method = RequestMethod.GET)
    public String getReportPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null) {
            return "jsp/index";
        }
        
        return "jsp/admin/report" ;
    }
    
    @RequestMapping(value="/verifyVehicleAction", method=RequestMethod.POST)
    public String verifyVehicleAction(HttpServletRequest request, ModelMap mm) {
        
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            String model = request.getParameter("model");
            String description = request.getParameter("description");
            int idvehicle = Integer.parseInt(request.getParameter("idvehicle")) ;
            
            boolean result = VehicleData.editVehicle(idvehicle, plate, model, description);
            if(result) {
                mm.put("message","Verify vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleData.getListPendingVehicle();
                session.setAttribute("listVehicle", listVehicle);
                
            }
            else
                mm.put("message", "Verify vehicle false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        return "jsp/admin/verifyVehicle";
    }
    
    @RequestMapping(value="/rejectVehicle", method=RequestMethod.POST)
    public String rejectVehicle(HttpServletRequest request, ModelMap mm) {
        
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            int idVehicle = Integer.parseInt(request.getParameter("idReject")) ; 
            
            boolean result = VehicleData.rejectVehicle(idVehicle);
            if(result) {
                mm.put("message","Reject vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleData.getListPendingVehicle();
                mm.put("listVehicle", listVehicle);
            }
            else
                mm.put("message", "Reject vehicle false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        return "jsp/admin/verifyVehicle";
    }
    
    @RequestMapping(value="/addStaff", method=RequestMethod.POST)
    public String addStaff(HttpServletRequest request, ModelMap mm) {
        
        try {
            HttpSession session = request.getSession();
            Admin ad = (Admin) session.getAttribute("admin");
            if(ad == null)
                return "jsp/index";
            
            request.setCharacterEncoding("UTF-8");
            String scode = request.getParameter("scode");
            String name = request.getParameter("fullname");
            int parkid = Integer.parseInt(request.getParameter("parkid")) ;
            System.out.println("Request: "+ scode + name + parkid);
            boolean checkstaffcode = StaffData.checkStaffCode(scode) ;
            if(checkstaffcode) {
                mm.put("message", "Staff Code Exist!");
            }
            else {
                boolean result = StaffData.addStaff(scode, name, parkid);
                if(result)
                    mm.put("message","Add staff success!" );
                else
                    mm.put("message", "Add staff false!");
            }
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        ArrayList<Park> listPark = ParkData.getListParkData();
        ArrayList<Staff> listStaff = StaffData.getListStaff();
        mm.put("listStaff", listStaff);          
        mm.put("listPark", listPark);
        
        return "jsp/admin/manageStaff" ;
    }
    
    @RequestMapping(value="/deactiveStaff", method=RequestMethod.POST)
    public String deactiveStaff(HttpServletRequest request, ModelMap mm) {
        
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            int idStaff = Integer.parseInt(request.getParameter("idDeactive")) ; 
            
            boolean result = StaffData.deactiveStaff(idStaff);
            if(result) {
                mm.put("message","Remove Staff success!" );
                ArrayList<Vehicle> listVehicle = VehicleData.getListPendingVehicle();
                mm.put("listVehicle", listVehicle);
            }
            else
                mm.put("message", "Remove Staff false!");
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        
        ArrayList<Park> listPark = ParkData.getListParkData();
        ArrayList<Staff> listStaff = StaffData.getListStaff();
        mm.put("listStaff", listStaff);          
        mm.put("listPark", listPark);
        
        return "jsp/admin/manageStaff";
    }
    
    
}
