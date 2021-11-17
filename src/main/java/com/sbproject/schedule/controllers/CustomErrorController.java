package com.sbproject.schedule.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = "Sorry, something went wrong!";
        String statusInfo = "Unknown error has occurred";
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                message =  "The resources you have requested have not been found!";
                statusInfo = "Response status code: 404, NOT FOUND";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                message = "Something went wrong on the server side...";
                statusInfo = "Response status code: 500, INTERNAL SERVER ERROR";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                message = "Authorization failed! Seems like you are not allowed to access the resources or perform the action";
                statusInfo = "Response status code: 403, FORBIDDEN";
            }
        }
        model.addAttribute("message", message);
        model.addAttribute("statusInfo",statusInfo);
        return "error";
    }


}
