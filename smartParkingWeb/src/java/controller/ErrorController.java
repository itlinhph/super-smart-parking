/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author linhph
 */
@Controller
public class ErrorController {
 
    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
         
        ModelAndView errorPage = new ModelAndView("jsp/errorPage");
        String errorMsg = "";
        int errorCode = (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
 
        switch (errorCode) {
            case 400: {
                errorMsg = "Error 400: Bad Request";
                break;
            }
            
            case 404: {
                errorMsg = "Error 404: Page resource not found!";
                break;
            }
            
            default: {
                errorMsg = "An unknow error occured!";
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }
     
  
}
