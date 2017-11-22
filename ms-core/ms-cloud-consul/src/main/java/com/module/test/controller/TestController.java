package com.module.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/hi")
    public String home() {
        return "hi ,i'm miya";
    }
}
