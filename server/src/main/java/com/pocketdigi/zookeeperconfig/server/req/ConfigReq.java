package com.pocketdigi.zookeeperconfig.server.req;

import lombok.Data;

/**
 * @author fhp
 * @date 2019-08-09
 */
@Data
public class ConfigReq {
    String application;
    String profile;
    String configData;
}
