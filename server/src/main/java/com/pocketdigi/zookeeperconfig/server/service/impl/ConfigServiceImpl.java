package com.pocketdigi.zookeeperconfig.server.service.impl;
import	java.util.ArrayList;

import com.pocketdigi.zookeeperconfig.server.exception.BizException;
import com.pocketdigi.zookeeperconfig.server.model.ApplicationNode;
import com.pocketdigi.zookeeperconfig.server.service.ConfigService;
import com.pocketdigi.zookeeperconfig.server.service.ZookeeperService;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fhp
 * @date 2019-08-09
 */
@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ZookeeperService zookeeperService;

    private final String PREFIX_CONFIG = "/config";

    @Override
    public void saveApplicationConfig(String application, String profile, String data) {
        String pathPrefix = getPathPrefix(application, profile);
        if(StringUtils.isBlank(data)) {
            //无数据,只创建节点
            zookeeperService.saveDataInPath(pathPrefix,null);
            return;
        }

        Properties properties = new Properties();
        try {

            properties.load(new StringReader(data));
            List<String> existedApplicationConfigKeys = getApplicationConfigKeys(application,
                profile);

            Set<Entry<Object, Object>> entries = properties.entrySet();
            for (Entry<Object, Object> entry : entries) {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                String path = pathPrefix + "/" + key;
                zookeeperService.saveDataInPath(path, value);
                existedApplicationConfigKeys.remove(key);
            }
            if (existedApplicationConfigKeys.size() > 0) {
                //删除多余配置
                existedApplicationConfigKeys.forEach(key -> {
                    zookeeperService.deletePath(pathPrefix + "/" + key);
                });
            }

        } catch (IOException e) {
            log.error(String.format("解析%s %s 配置失败", application, profile), e);
            throw new BizException("解析properties文件失败");
        }
    }


    private List<String> getApplicationConfigKeys(String application, String profile) {
        String pathPrefix = getPathPrefix(application, profile);
        return zookeeperService.getChildrenInPath(pathPrefix);
    }

    @Override
    public String getApplicationConfig(String application, String profile) {
        String pathPrefix = getPathPrefix(application, profile);
        StringBuilder builder = new StringBuilder();
        List<String> propertyKeys = getApplicationConfigKeys(application, profile);
        Collections.sort(propertyKeys);
        for (String key : propertyKeys) {
            String path = pathPrefix + "/" + key;
            String value = zookeeperService.getDataInPath(path);
            if (StringUtils.isBlank(value)) {
                zookeeperService.deletePath(path);
                continue;
            }
            builder.append(key).append("=").append(value).append("\n");
        }
        return builder.toString();
    }

    @Override
    public List<ApplicationNode> getApplicationTree() {
        List<String> childrenInPath = zookeeperService.getChildrenInPath(PREFIX_CONFIG);
        TreeMap<String,List<ApplicationNode>> applicationMap=new TreeMap<>();
        childrenInPath.forEach(node -> {
            String[] split = node.split(",");
            String application;
            String profile;
            if(split.length == 2) {
                //带profile
                application=split [0];
                profile = split [1];
            }else{
                application=node;
                profile="default";
            }
            List<ApplicationNode> applicationNodes = applicationMap.computeIfAbsent(application,s -> new ArrayList<>());
            ApplicationNode applicationNode=new ApplicationNode();
            applicationNode.setLabel(profile);
            applicationNode.setPath(node);
            applicationNode.setApplication(application);
            applicationNode.setProfile(profile);
            applicationNodes.add(applicationNode);
        });
        List<ApplicationNode> applicationTree=new ArrayList<>();
        Set<String> keys = applicationMap.keySet();
        keys.forEach(application -> {
            ApplicationNode applicationNode=new ApplicationNode();
            applicationNode.setLabel(application);
            applicationNode.setPath(application);
            applicationNode.setApplication(application);
            applicationNode.setProfile(null);
            applicationNode.setChildren(applicationMap.get(application));
            applicationTree.add(applicationNode);
        });

        return applicationTree;
    }

    @Override
    public void deleteApplicationConfig(String application, String profile) {
        String pathPrefix = getPathPrefix(application, profile);
        zookeeperService.deletePath(pathPrefix);
    }

    private String getPathPrefix(String application, String profile) {
        String pathPrefix = PREFIX_CONFIG+"/" + application;
        if (StringUtils.isNotBlank(profile)) {
            pathPrefix += "," + profile;
        }
        return pathPrefix;
    }
}
