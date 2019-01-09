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
import model.Report;
import model.ReportDAO;
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
@RequestMapping(value = "/report")
public class ReportController {

    @RequestMapping(value = "/userReport", method = RequestMethod.GET)
    public String getReportPage(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {

        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("user");
            if (us == null)
                return "jsp/index";

            request.setCharacterEncoding("UTF-8");
            int ticketId = Integer.parseInt(request.getParameter("ticket"));
            int userid = us.getUserId();
            Report r = ReportDAO.getReportByTicketId(ticketId, userid);

            if (r == null) {
                response.sendRedirect(request.getContextPath() + "/ticket/manageTiket");
            } else {
                mm.put("report", r);
                mm.put("ticketId", ticketId);
            }

        } catch (Exception e) {
            System.out.println("Exeption getReportPage: " + e.getMessage());
        }

        return "jsp/user/report";
    }

    @RequestMapping(value = "/userReportAction", method = RequestMethod.POST)
    public String reportAction(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {

        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("user");
            if (us == null)
                return "jsp/index";

            int userid = us.getUserId();

            request.setCharacterEncoding("UTF-8");
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            String type = request.getParameter("type");
            String description = request.getParameter("description");

            boolean result = ReportDAO.createReport(type, ticketId, description);
            if (result) {
                response.sendRedirect(request.getContextPath() + "/report/userReport?ticket=" + ticketId);
            } else
                mm.put("message", "Can't report to admin!");
        } catch (Exception e) {
            System.out.println("EXEPTION: " + e.getMessage());
        }
        return "jsp/user/report";
    }

    @RequestMapping(value = "/adReportPage", method = RequestMethod.GET)
    public String getReportPage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "jsp/index";
        }
        ArrayList<Report> listReport = ReportDAO.getAllReport();
        mm.put("listReport", listReport);
        for (Report r : listReport) {
            System.out.println("r: " + r.getId());
        }
        return "jsp/admin/adreport";
    }

    @RequestMapping(value = "/adReplyReport", method = RequestMethod.POST)
    public String replyReport(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {

        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                return "jsp/index";
            }

            request.setCharacterEncoding("UTF-8");
            int reportId = Integer.parseInt(request.getParameter("reportId"));
            String adminReply = request.getParameter("adminReply");
            System.out.println(reportId + adminReply);

            boolean result = ReportDAO.setReplyAdmin(reportId, adminReply);
            if (result)
                response.sendRedirect(request.getContextPath() + "/report/adReportPage");

            else {
                mm.put("message", "Reply report false!");
                ArrayList<Report> listReport = ReportDAO.getAllReport();
                mm.put("listReport", listReport);

            }
        } catch (Exception e) {
            System.out.println("EXEPTION: " + e.getMessage());
        }

        return "jsp/admin/adreport";
    }

}
