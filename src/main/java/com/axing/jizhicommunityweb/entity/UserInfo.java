package com.axing.jizhicommunityweb.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private Integer id;

    private String uid;

    private String email;

    private String pass;

    private String token;

    private String username;

    private String avatar;

    private String signature;

    private String city;

    private String vip;

    private String registerTimestamp;

    private String vercode;

    public UserInfo() {
    }

    public UserInfo(Integer id, String uid, String email, String pass, String token, String username, String avatar, String signature, String city, String vip, String registerTimestamp) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.pass = pass;
        this.token = token;
        this.username = username;
        this.avatar = avatar;
        this.signature = signature;
        this.city = city;
        this.vip = vip;
        this.registerTimestamp = registerTimestamp;
        this.vercode = vercode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getRegisterTimestamp() {
        return registerTimestamp;
    }

    public void setRegisterTimestamp(String registerTimestamp) {
        this.registerTimestamp = registerTimestamp;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", signature='" + signature + '\'' +
                ", city='" + city + '\'' +
                ", vip='" + vip + '\'' +
                ", registerTimestamp='" + registerTimestamp + '\'' +
                ", vercode='" + vercode + '\'' +
                '}';
    }
}

