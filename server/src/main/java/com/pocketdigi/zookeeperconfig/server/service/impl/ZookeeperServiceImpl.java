package com.pocketdigi.zookeeperconfig.server.service.impl;

import com.pocketdigi.zookeeperconfig.server.service.ZookeeperService;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fhp
 * @date 2019-08-09
 */
@Slf4j
@Service
public class ZookeeperServiceImpl implements ZookeeperService {

    @Autowired
    CuratorFramework client;


    @Override
    public void saveDataInPath(String path, String data) {
        try {
            client.createContainers(path);
            if(StringUtils.isNotBlank(data)) {
                client.setData().forPath(path, data.getBytes());
            }
        } catch (Exception e) {
            log.error(String.format("保存 %s 失败", path), e);
        }
    }


    @Override
    public String getDataInPath(String path) {
        try {
            byte[] bytes = client.getData().forPath(path);
            return bytes == null ? null : new String(bytes);
        } catch (Exception e) {
            log.error(String.format("获取路径 %s 数据失败", path), e);
        }
        return null;
    }

    @Override
    public void deletePath(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            log.error(String.format("删除路径 %s 失败", path), e);
        }
    }

    @Override
    public List<String> getChildrenInPath(String path) {
        try {
            List<String> propertyKeys = client.getChildren().forPath(path);
            return propertyKeys;
        } catch (Exception e) {
            log.error(String.format("读取节点 %s 配置失败", path), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Stat checkExists(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            return stat;
        } catch (Exception e) {
            log.error(String.format("检查节点 %s 失败", path), e);
        }
        return null;
    }

}
