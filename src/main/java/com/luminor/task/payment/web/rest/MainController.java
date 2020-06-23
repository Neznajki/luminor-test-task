package com.luminor.task.payment.web.rest;

import com.luminor.task.payment.security.SecurityServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("")
    public String homepage() {
        return "welcome";
    }
}
