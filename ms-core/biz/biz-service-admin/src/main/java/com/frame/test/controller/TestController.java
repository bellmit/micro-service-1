package com.frame.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.monitor.log.MsLog;

/**
 * 测试
 * @author yuejing
 * @date 2017年2月20日 上午9:08:03
 */
@Controller
public class TestController {

    private final MsLog MSLOG = MsLog.getMsLog(TestController.class);

    /*@RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }*/
    
    //http://127.0.0.1:7950/test/index
    @RequestMapping(value = "/test/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("message", "您好");
		MSLOG.info(modelMap);
        return "index";
    }
}
