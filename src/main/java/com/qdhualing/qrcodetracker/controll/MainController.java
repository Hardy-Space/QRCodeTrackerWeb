package com.qdhualing.qrcodetracker.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/1/26.
 */
@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/login")
    @ResponseBody
    public String getData() {
        return "index";
    }

}
