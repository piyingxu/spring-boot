package com.util.temp;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/1/9 16:58
 */
public class Security extends BaseEntity {

    @ApiModelProperty(value = "登录名", example = "pyx")
    private String login;

    @ApiModelProperty(value = "密码", example = "pyx")
    private String Password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
