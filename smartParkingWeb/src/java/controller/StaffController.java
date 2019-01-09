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
import model.Staff;
import model.StaffDAO;
import model.User;
import model.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author linhph
 */

@Controller
@RequestMapping(value = "/staff")
public class StaffController {

    @RequestMapping(value = "/adManageStaff", method = RequestMethod.GET)
    public String getManageStaffPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "jsp/index";
        }
        ArrayList<Park> listPark = ParkDAO.getListParkData();
        ArrayList<Staff> listStaff = StaffDAO.getListStaff();
        mm.put("listStaff", listStaff);
        mm.put("listPark", listPark);
        return "jsp/admin/manageStaff";
    }

    @RequestMapping(value = "/adAddStaff", method = RequestMethod.POST)
    public String addStaff(HttpServletRequest request, ModelMap mm) {

        try {
            HttpSession session = request.getSession();
            Admin ad = (Admin) session.getAttribute("admin");
            if (ad == null)
                return "jsp/index";

            request.setCharacterEncoding("UTF-8");
            String scode = request.getParameter("scode");
            String name = request.getParameter("fullname");
            int parkid = Integer.parseInt(request.getParameter("parkid"));
            System.out.println("Request: " + scode + name + parkid);
            boolean checkstaffcode = StaffDAO.checkStaffCode(scode);
            if (checkstaffcode) {
                mm.put("message", "Staff Code Exist!");
            } else {
                boolean result = StaffDAO.addStaff(scode, name, parkid);
                if (result)
                    mm.put("message", "Add staff success!");
                else
                    mm.put("message", "Add staff false!");
            }
        } catch (Exception e) {
            System.out.println("EXEPTION: " + e.getMessage());
        }
        ArrayList<Park> listPark = ParkDAO.getListParkData();
        ArrayList<Staff> listStaff = StaffDAO.getListStaff();
        mm.put("listStaff", listStaff);
        mm.put("listPark", listPark);

        return "jsp/admin/manageStaff";
    }

    @RequestMapping(value = "/adSetStatusStaff", method = RequestMethod.POST)
    public String setStatusStaff(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {

        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                return "jsp/index";
            }

            request.setCharacterEncoding("UTF-8");
            int idStaff = Integer.parseInt(request.getParameter("idStaff"));
            String status = request.getParameter("status");

            boolean result = StaffDAO.setStatusStaff(idStaff, status);
            if (result)
                response.sendRedirect(request.getContextPath() + "/admin/manageStaff");

            else {
                mm.put("message", "Action with this user false!");
                ArrayList<User> listUser = UserDAO.getListUser();
                mm.put("listUser", listUser);

            }
        } catch (Exception e) {
            System.out.println("EXEPTION: " + e.getMessage());
        }

        return "jsp/admin/manageStaff";
    }

}
