package com.im.demo.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/")
@Controller
public class HomeController {

    /**
     * 기본 URL
     */
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return doIndex();
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.POST)
    public String doIndex() {
        return "index";
        //return "redirect:user/notices";
    }

}
