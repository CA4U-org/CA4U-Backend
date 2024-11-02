package com.example.ca4u_backend.login_test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }
}
