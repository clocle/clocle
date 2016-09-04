package com.bean;

import cn.bmob.v3.BmobObject;



public class Bmob_UserBean extends BmobObject {
    private String username;
private int i;
    public Bmob_UserBean() {

       this.setTableName("user");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

}
