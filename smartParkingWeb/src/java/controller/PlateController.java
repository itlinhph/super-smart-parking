/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Staff;
import model.WrongPlate;
import model.WrongPlateDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author linhph
 */
@Controller
@RequestMapping(value = "/plate")
public class PlateController {
    @RequestMapping(value = "/staffFixPlate", method = RequestMethod.GET)
    public String fixWrongPlatePage(HttpServletRequest request, ModelMap mm) {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            return "jsp/index";
        }

        ArrayList<WrongPlate> listWrongPlate = WrongPlateDAO.getListWrongPlateByParkId(staff.getParkid());
        mm.put("listWrongPlate", listWrongPlate);

        return "jsp/staff/fixWrongPlate";
    }

    @RequestMapping(value = "/editWrongPlate", method = RequestMethod.POST)
    public String editWrongPlate(HttpServletRequest request, ModelMap mm) {

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            return "jsp/index";
        }

        try {
            request.setCharacterEncoding("UTF-8");
            String plate = request.getParameter("plate");
            String plate_id = request.getParameter("plate_id");
            int plateId = Integer.parseInt(plate_id);
            int parkid = staff.getParkid();

            boolean editFixedPlateResult = WrongPlateDAO.editFixPlate(plate, plateId, parkid);

        } catch (Exception e) {
            System.out.println("Exeption editWrongPlate: " + e.getMessage());
        }
        mm.put("script", "window.location = 'staffFixPlate';");
        return "jsp/staff/fixWrongPlate";
    }
}
