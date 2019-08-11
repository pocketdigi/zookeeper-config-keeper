package com.pocketdigi.zookeeperconfig.server;

import com.pocketdigi.zookeeperconfig.server.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fhp
 * @date 2019-08-09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class ZkTest {
    @Autowired
    ZookeeperService zookeeperService;

    @Test
    public void save() {
        zookeeperService.saveDataInPath("/data/asdfe","aaaafdaf");
    }
}
