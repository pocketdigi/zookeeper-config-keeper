package com.pocketdigi.zookeeperconfig.server.service;

import java.util.List;
import org.apache.zookeeper.data.Stat;

/**
 * @author fhp
 * @date 2019-08-09
 */
public interface ZookeeperService {
    /**
     * 指定位置保存数据
     * @param path
     * @param data
     */
    void saveDataInPath(String path,String data);


    /**
     * 获取指定节点的数据
     * @param path
     * @return
     */
    String getDataInPath(String path);

    /**
     * 删除路径
     * @param path
     */
    void deletePath(String path);

    /**
     * 获取指定节点下的子节点
     * @param path
     * @return
     */
    List<String> getChildrenInPath(String path);

    Stat checkExists(String path);
}
