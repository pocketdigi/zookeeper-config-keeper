package com.pocketdigi.configuration.demo.controller;

import com.pocketdigi.configuration.demo.config.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

    @Autowired
    Test test;
    @RequestMapping("key")
    public Object key(){
        return key;
    }

    @RequestMapping("key1")
    public Object key1(){
        return test.getKey1();
    }
}
