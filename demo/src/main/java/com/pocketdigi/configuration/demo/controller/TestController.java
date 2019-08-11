package com.pocketdigi.configuration.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fhp
 * @date 2019-08-09
 */
@RequestMapping("test")
@RestController
public class TestController {
    @Value("${key}")
    String key;
    @RequestMapping("key")
    public Object key(){
        return key;
    }
}
