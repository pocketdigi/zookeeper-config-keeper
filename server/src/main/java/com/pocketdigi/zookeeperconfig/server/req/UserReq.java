package com.pocketdigi.zookeeperconfig.server.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fhp
 * @date 2019-08-10
 */
@ApiModel("用户")
@Data
public class UserReq {
    @ApiModelProperty("用户名")
    String username;
    @ApiModelProperty("密码")
    String password;
}
