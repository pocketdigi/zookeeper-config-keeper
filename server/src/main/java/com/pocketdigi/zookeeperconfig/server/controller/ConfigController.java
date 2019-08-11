package com.pocketdigi.zookeeperconfig.server.controller;

import com.pocketdigi.zookeeperconfig.server.model.ApplicationNode;
import com.pocketdigi.zookeeperconfig.server.req.ConfigReq;
import com.pocketdigi.zookeeperconfig.server.service.ConfigService;
import com.pocketdigi.zookeeperconfig.server.vo.ResultVO;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fhp
 * @date 2019-08-09
 */
@RequestMapping("config")
@RestController
public class ConfigController {
    @Autowired
    ConfigService configService;

    @PostMapping("")
    public ResultVO saveConfig(@RequestBody ConfigReq configReq) {
        String profile = configReq.getProfile();
        if(StringUtils.equals("default",profile)) {
            profile=null;
        }
        configService
            .saveApplicationConfig(configReq.getApplication(), profile,configReq.getConfigData());
        return ResultVO.wrapSuccess();
    }

    @GetMapping("/{application}/{profile}")
    public ResultVO getConfig(@PathVariable String application,
        @PathVariable(required = false) String profile) {
        if(StringUtils.equals("default",profile)) {
            profile=null;
        }
        String applicationConfig = configService.getApplicationConfig(application, profile);
        return ResultVO.wrapSuccess(applicationConfig);
    }
    @GetMapping("/{application}")
    public ResultVO getConfig(@PathVariable String application) {
        String applicationConfig = configService.getApplicationConfig(application, null);
        return ResultVO.wrapSuccess(applicationConfig);
    }
    @GetMapping("/tree")
    public ResultVO<List<ApplicationNode>> getApplicationTree() {
        List<ApplicationNode> applicationTree = configService.getApplicationTree();
        return ResultVO.wrapSuccess(applicationTree);
    }

    @DeleteMapping("/{application}/{profile}")
    public ResultVO deleteConfig(@PathVariable String application,
        @PathVariable(required = false) String profile) {
        if(StringUtils.equals("default",profile)) {
            profile=null;
        }
        configService.deleteApplicationConfig(application, profile);
        return ResultVO.wrapSuccess();
    }
}
