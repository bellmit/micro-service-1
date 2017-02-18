package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lilufeng on 2016/8/26.
 */
@RestController
public class HomeController {

    @RequestMapping("/index")
    public Object index() {
        return "index";
    }

    @RequestMapping("/home")
    public Object home() {
        return "home";
    }
}
