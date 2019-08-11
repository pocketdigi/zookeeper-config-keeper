package com.pocketdigi.zookeeperconfig.server.service;

/**
 * @author fhp
 * @date 2019-08-08
 */
public interface UserService {

    /**
     * 登录
     * @param username
     * @param password
     * @return jwt token
     */
    String login(String username, String password);

    /**
     * 检查是否初次进入(是否配置管理员账户)
     * @return
     */
    Boolean setupCheck();

    /**
     * 添加用户
     * @param username
     * @param password
     */
    void addUser(String username, String password);
}
