package com.example.login_page1;

public class UserModelJV {
    String userid, password, imgUrl, email;

    public UserModelJV()
    {

    }
    public UserModelJV(String userid, String password, String imgUrl, String email) {
        this.userid = userid;
        this.password = password;
        this.imgUrl = imgUrl;
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
