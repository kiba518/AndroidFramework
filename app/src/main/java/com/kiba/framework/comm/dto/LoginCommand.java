package com.kiba.framework.comm.dto;

import com.kiba.framework.comm.dto.base.BaseCommand;

public class LoginCommand extends BaseCommand {
    public String LoginName;
    public String Password;
    public String KeyInfo;
    public int ViewType;

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getKeyInfo() {
        return KeyInfo;
    }

    public void setKeyInfo(String keyInfo) {
        KeyInfo = keyInfo;
    }

    public int getViewType() {
        return ViewType;
    }

    public void setViewType(int viewType) {
        ViewType = viewType;
    }
}
