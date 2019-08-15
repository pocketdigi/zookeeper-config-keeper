package com.pocketdigi.configuration.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fhp
 * @date 2019-08-15
 */
@ConfigurationProperties(prefix = "test")
public class Test {
    String key1;
    String key2;

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
}
