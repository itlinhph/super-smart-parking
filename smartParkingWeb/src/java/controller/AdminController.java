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
import model.Park;
import model.ParkDAO;
import model.Report;
import model.ReportDAO;
import model.Staff;
import model.StaffDAO;
import model.Ticket;
import model.TicketDAO;
import model.User;
import model.UserDAO;
import model.Vehicle;
import model.VehicleDAO;
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
        ArrayList<Park> listPark = ParkDAO.getListParkData();
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
        Park park = ParkDAO.getParkById(parkid);
        mm.put("park", park);
        
        ArrayList<Ticket> listTicket = TicketDAO.getListTicketByParkId(park.getId());
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
        ArrayList<Vehicle> listVehicle = VehicleDAO.getListPendingVehicle();
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
        ArrayList<Park> listPark = ParkDAO.getListParkData();
        ArrayList<Staff> listStaff = StaffDAO.getListStaff();
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
        
        ArrayList<User> listUser = UserDAO.getListUser();
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
        ArrayList<Report> listReport = ReportDAO.getAllReport();
        mm.put("listReport", listReport) ;
        for(Report r: listReport) {
            System.out.println("r: "+ r.getId());
    }
        return "jsp/admin/adreport" ;
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
            
            boolean result = VehicleDAO.editVehicle(idvehicle, plate, model, description);
            if(result) {
                mm.put("message","Verify vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleDAO.getListPendingVehicle();
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
            
            boolean result = VehicleDAO.rejectVehicle(idVehicle);
            if(result) {
                mm.put("message","Reject vehicle success!" );
                ArrayList<Vehicle> listVehicle = VehicleDAO.getListPendingVehicle();
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
            boolean checkstaffcode = StaffDAO.checkStaffCode(scode) ;
            if(checkstaffcode) {
                mm.put("message", "Staff Code Exist!");
            }
            else {
                boolean result = StaffDAO.addStaff(scode, name, parkid);
                if(result)
                    mm.put("message","Add staff success!" );
                else
                    mm.put("message", "Add staff false!");
            }
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        ArrayList<Park> listPark = ParkDAO.getListParkData();
        ArrayList<Staff> listStaff = StaffDAO.getListStaff();
        mm.put("listStaff", listStaff);          
        mm.put("listPark", listPark);
        
        return "jsp/admin/manageStaff" ;
    }
    
    @RequestMapping(value="/setStatusStaff", method=RequestMethod.POST)
    public String setStatusStaff(HttpServletRequest request,HttpServletResponse response, ModelMap mm) {
        
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            int idStaff = Integer.parseInt(request.getParameter("idStaff")) ; 
            String status = request.getParameter("status");
            
            boolean result = StaffDAO.setStatusStaff(idStaff, status);
            if(result)
                response.sendRedirect(request.getContextPath()+"/admin/manageStaff");
            
            else {
                mm.put("message", "Action with this user false!");
                ArrayList<User> listUser = UserDAO.getListUser();
                mm.put("listUser", listUser);
                
            }
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        
        return "jsp/admin/manageStaff";
    }
    
    
    @RequestMapping(value="/setStatusUser", method=RequestMethod.POST)
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
                response.sendRedirect(request.getContextPath()+"/admin/manageUser");
            
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
    
    @RequestMapping(value="/replyReport", method=RequestMethod.POST)
    public String replyReport(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
         
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin == null) {
                return "jsp/index";
            }
            
            request.setCharacterEncoding("UTF-8");
            int reportId = Integer.parseInt(request.getParameter("reportId")) ; 
            String adminReply = request.getParameter("adminReply");
            System.out.println(reportId + adminReply);
            
            boolean result = ReportDAO.setReplyAdmin(reportId, adminReply) ;
            if(result)
                response.sendRedirect(request.getContextPath()+"/admin/report");
            
            else {
                mm.put("message", "Reply report false!");
                ArrayList<Report> listReport = ReportDAO.getAllReport();
                mm.put("listReport", listReport);
                
            }
        }
        catch(Exception e) {
            System.out.println("EXEPTION: "+ e.getMessage());
        }
        
        return "jsp/admin/adreport";
    }
    
    
}
