package com.pocketdigi.zookeeperconfig.server.controller;

import com.pocketdigi.zookeeperconfig.server.req.UserReq;
import com.pocketdigi.zookeeperconfig.server.service.UserService;
import com.pocketdigi.zookeeperconfig.server.vo.ResultVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fhp
 * @date 2019-08-08
 */
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public ResultVO login(@Valid @RequestBody UserReq loginReq) {
        String token = userService.login(loginReq.getUsername(), loginReq.getPassword());
        return ResultVO.wrapSuccess(token);
    }

    @GetMapping("setup_check")
    public ResultVO<Boolean> setupCheck() {
        return ResultVO.wrapSuccess(userService.setupCheck());
    }

    @PostMapping("")
    public ResultVO addUser(@RequestBody UserReq user) {
        userService.addUser(user.getUsername(),user.getPassword());
        return ResultVO.wrapSuccess();
    }

}
