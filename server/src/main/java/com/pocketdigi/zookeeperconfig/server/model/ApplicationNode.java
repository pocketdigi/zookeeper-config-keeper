package com.pocketdigi.zookeeperconfig.server.model;

import java.util.List;
import lombok.Data;

/**
 * @author fhp
 * @date 2019-08-10
 */
@Data
public class ApplicationNode {
    String path;
    String label;
    String application;
    String profile;
    List<ApplicationNode> children;
}
