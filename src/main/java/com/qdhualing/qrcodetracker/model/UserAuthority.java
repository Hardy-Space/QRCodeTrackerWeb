package com.qdhualing.qrcodetracker.model;

/**
 * @author 马鹏昊
 * @desc
 * @date 2018/2/26
 */
public class UserAuthority {

    private String GroupName;

    private String AuthMobile;

    private int Group2;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getAuthMobile() {
        return AuthMobile;
    }

    public void setAuthMobile(String authMobile) {
        AuthMobile = authMobile;
    }

    public int getGroup2() {
        return Group2;
    }

    public void setGroup2(int group2) {
        Group2 = group2;
    }
}
