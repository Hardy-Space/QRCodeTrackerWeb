package com.qdhualing.qrcodetracker.model;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/1/27.
 */
public class User {

    private int UserID ;
    private int GroupID ;

    private String LoginName ;

    private String Password;
    private String TrueName;
    private String Address;
    private String Mobile;
    private String Email;
    private String QQ;
    private Timestamp RegTime;
    private Timestamp LastLoginTime;

    private int UserStatus;
    private int LoginCount;
    private int GroupCode;
    private int Group_1;
    private int Group_2;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

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

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String trueName) {
        TrueName = trueName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public Timestamp getRegTime() {
        return RegTime;
    }

    public void setRegTime(Timestamp regTime) {
        RegTime = regTime;
    }

    public Timestamp getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    public int getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(int userStatus) {
        UserStatus = userStatus;
    }

    public int getLoginCount() {
        return LoginCount;
    }

    public void setLoginCount(int loginCount) {
        LoginCount = loginCount;
    }

    public int getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(int groupCode) {
        GroupCode = groupCode;
    }

    public int getGroup_1() {
        return Group_1;
    }

    public void setGroup_1(int group_1) {
        Group_1 = group_1;
    }

    public int getGroup_2() {
        return Group_2;
    }

    public void setGroup_2(int group_2) {
        Group_2 = group_2;
    }
}
