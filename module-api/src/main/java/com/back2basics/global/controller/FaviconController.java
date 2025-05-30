package com.back2basics.global.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @RequestMapping("favicon.ico")
    public void favicon() {
        // 아무것도 하지 않도록 하여 에러 방지
    }
}
