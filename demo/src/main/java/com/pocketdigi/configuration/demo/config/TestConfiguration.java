package com.pocketdigi.configuration.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author fhp
 * @date 2019-08-15
 */
@Configuration
@EnableConfigurationProperties(Test.class)
public class TestConfiguration {

}
