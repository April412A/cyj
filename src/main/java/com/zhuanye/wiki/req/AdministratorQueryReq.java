package com.zhuanye.wiki.req;

public class AdministratorQueryReq extends PageReq {

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "AdministratorQueryReq{" +
                "loginName='" + loginName + '\'' +
                "} " + super.toString();
    }
}
