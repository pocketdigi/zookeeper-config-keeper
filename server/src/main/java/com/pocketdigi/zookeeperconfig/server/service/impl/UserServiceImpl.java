package com.pocketdigi.zookeeperconfig.server.service.impl;

import com.pocketdigi.zookeeperconfig.server.exception.BizException;
import com.pocketdigi.zookeeperconfig.server.model.ErrorEnum;
import com.pocketdigi.zookeeperconfig.server.service.UserService;
import com.pocketdigi.zookeeperconfig.server.service.ZookeeperService;
import com.pocketdigi.zookeeperconfig.server.util.JwtUtil;
import com.pocketdigi.zookeeperconfig.server.util.MD5Util;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fhp
 * @date 2019-08-08
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ZookeeperService zookeeperService;

    private static final String  ROOT_PATH= "/zkck";

    @Override
    public String login(String username, String password) {
        String password1 = getPassword(username);
        if(StringUtils.equals(MD5Util.getMD5(password), password1)) {
            //验证通过
            return jwtUtil.generateToken(username);
        }
        throw new BizException(ErrorEnum.USER_LOGIN_ERROR);
    }

    @Override
    public Boolean setupCheck() {
        String path=ROOT_PATH +"/users";
        List<String> childrenInPath = zookeeperService.getChildrenInPath(path);
        return !Collections.isEmpty(childrenInPath);
    }

    @Override
    public void addUser(String username, String password) {
        Stat stat = zookeeperService.checkExists(ROOT_PATH +"/users");
        if(stat!=null) {
            throw new BizException("系统已经初始化，无法再添加用户");
        }
        String userPath=ROOT_PATH +"/users/"+username;
        zookeeperService.saveDataInPath(userPath, MD5Util.getMD5(username));
    }

    private String getPassword(String username) {
        String userPath=ROOT_PATH +"/users/"+username;
        return zookeeperService.getDataInPath(userPath);
    }
}
