package com.dto;

import com.entity.SysUser;

public class SysUserDTO extends SysUser {
    private String captcha;
    private boolean rememberMe;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
