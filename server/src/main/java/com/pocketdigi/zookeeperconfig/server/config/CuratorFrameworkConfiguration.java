package com.pocketdigi.zookeeperconfig.server.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fhp
 * @date 2019-08-09
 */
@Configuration
public class CuratorFrameworkConfiguration {

    @Value("${zookeeper.connectionString}")
    String zookeeperConnectionString;

    @Bean(destroyMethod = "close")
    CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory
            .newClient(zookeeperConnectionString, retryPolicy);
        client.start();
        return client;
    }
}
