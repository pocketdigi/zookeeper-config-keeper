package com.pocketdigi.zookeeperconfig.server.service;

import com.pocketdigi.zookeeperconfig.server.model.ApplicationNode;
import java.util.List;

/**
 * @author fhp
 * @date 2019-08-09
 */
public interface ConfigService {
    /**
     * 保存应用配置
     * @param application 目标应用名
     * @param profile profile名
     * @param data 配置内容
     */
    void saveApplicationConfig(String application,String profile,String data);

    /**
     * 获取指定应用的配置
     * @param application
     * @param profile
     * @return
     */
    String getApplicationConfig(String application,String profile);

    List<ApplicationNode> getApplicationTree();

    void deleteApplicationConfig(String application,String profile);
}
