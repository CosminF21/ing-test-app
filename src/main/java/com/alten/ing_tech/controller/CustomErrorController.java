package com.alten.ing_tech.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    private static Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model){
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        model.addAttribute("err", httpStatus);

        logger.info("error {} - {}", httpStatus.value(), httpStatus.getReasonPhrase());

        return "error";
    }
}
