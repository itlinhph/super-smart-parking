/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
    public String userInforPage(ModelMap mm) {
        mm.put("menuitem", "parkmenu");
        return "jsp/staff/staffPark" ;
    }
    
    
}
