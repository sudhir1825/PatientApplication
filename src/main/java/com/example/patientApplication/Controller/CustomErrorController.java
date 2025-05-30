package com.example.patientApplication.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCodeObj = request.getAttribute("javax.servlet.error.status_code");
        Object path = request.getAttribute("javax.servlet.error.request_uri");
        Object message = request.getAttribute("customErrorMessage");

        int statusCode = statusCodeObj != null ? Integer.parseInt(statusCodeObj.toString()) : 500;
        String errorPath = path != null ? path.toString() : "Unknown";
        String errorMessage = message != null ? message.toString() : getDefaultMessage(statusCode);

        model.addAttribute("status", statusCode);
        model.addAttribute("path", errorPath);
        model.addAttribute("message", errorMessage);

        return "error"; // Thymeleaf template error.html
    }

    private String getDefaultMessage(int statusCode) {
        return switch (statusCode) {
            case 404 -> "The page you are looking for was not found.";
            case 403 -> "You do not have permission to access this resource.";
            case 500 -> "An internal server error occurred.";
            default -> "Unexpected error occurred.";
        };
    }
}
