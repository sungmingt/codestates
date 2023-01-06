package com.codestates.docker_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping
    public String test(Model model){
        try {
            return "index";
        } catch (Exception e) {
            return null;
        }
    }

}
