package com.frame.test.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

/**
 * 测试
 * @author yuejing
 * @date 2017年2月20日 上午9:08:03
 */
@Controller
public class TestController {

    private final Logger LOGGER = Logger.getLogger(getClass());

    /*@RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }*/
    
    @RequestMapping(value = "/test/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("message", "您好");
        return "index";
    }
}
