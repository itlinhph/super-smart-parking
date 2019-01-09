/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Staff;
import model.Ticket;
import model.TicketDAO;
import model.User;
import model.UserDAO;
import model.Vehicle;
import model.VehicleDAO;
import model.WrongPlateDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import otherAddOn.GearmanConnect;

/**
 *
 * @author linhph
 */
@Controller
@RequestMapping(value = "/checkInOut")
public class CheckInOutController {
    @RequestMapping(value = "/checkInOutPage", method = RequestMethod.GET)
    public String checkoutInOutPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            return "jsp/index";
        }

        return "jsp/staff/checkinout";
    }

    @RequestMapping(value = "/checkoutVehicle", method = RequestMethod.POST)
    public String checkoutVehicle(HttpServletRequest request, ModelMap mm, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            return "jsp/index";
        }

        try {
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            int ticketId = Integer.parseInt(request.getParameter("ticketid"));
            int parkid = staff.getParkid();

            boolean checkoutResult = VehicleDAO.checkoutVehicle(ticketId);
            response.sendRedirect(request.getContextPath() + "/park/staffParkInfor");
        } catch (Exception e) {
            System.out.println("Exeption checkoutVehicle: " + e.getMessage());
        }

        return "jsp/staff/checkoutAction";
    }

    @RequestMapping(value = "/checkoutAction", method = RequestMethod.GET)
    public String checkoutAction(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            return "jsp/index";
        }
        try {
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            String img = request.getParameter("img");

            Ticket t = TicketDAO.getTicketByPlate(plate);
            Vehicle v = VehicleDAO.getVehicleByPlate(plate);
            User u = UserDAO.getUserByPlate(plate);

            mm.put("vehicle", v);
            mm.put("ticket", t);
            mm.put("user", u);
            mm.put("checkoutImg", img);

        } catch (Exception e) {
            System.out.println("Exeption checkoutAction: " + e.getMessage());
        }

        return "jsp/staff/checkoutAction";
    }

    @RequestMapping(value = "/checkin", method = RequestMethod.POST)
    public String checkin(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            return "jsp/index";
        }
        try {
            request.setCharacterEncoding("UTF-8");
            String image = request.getParameter("imgCheckin");
            String plate = GearmanConnect.getPlateByGearman(image);
            System.out.println("plate: " + plate);
            int vehicleId = VehicleDAO.checkPlateExist(plate);
            if (vehicleId == 0) { // wrong plate
                WrongPlateDAO.addWrongPlate(image, plate, staff.getParkid());
                response.sendRedirect(request.getContextPath() + "/plate/staffFixPlate");
            } else {
                TicketDAO.createTicket(vehicleId, staff.getParkid());
                response.sendRedirect(request.getContextPath() + "/park/staffParkInfor");
            }

        } catch (Exception e) {
            System.out.println("Exeption checkoutAction: " + e.getMessage());
        }
        return "jsp/staff/checkinout";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            return "jsp/index";
        }
        try {
            request.setCharacterEncoding("UTF-8");
            String image = request.getParameter("imgCheckout");
            String plate = request.getParameter("plate");
            if (!image.equals("")) {
                plate = GearmanConnect.getPlateByGearman(image);
                System.out.println("plate: " + plate);

            } else {
                image = request.getParameter("imgCheckout2");
            }
            int vehicleId = VehicleDAO.checkPlateExist(plate);
            if (vehicleId == 0) { // wrong plate
                mm.put("message", "Plate not found!");
                mm.put("detect_plate", plate);
                mm.put("image_file", image);
            } else {
                String redirectPage = request.getContextPath() + "/checkInOut/checkoutAction?plate=" + plate + "&img="
                        + image;
                response.sendRedirect(redirectPage);
            }
        } catch (Exception e) {
            System.out.println("Exeption checkout: " + e.getMessage());
        }
        return "jsp/staff/checkinout";
    }

}
