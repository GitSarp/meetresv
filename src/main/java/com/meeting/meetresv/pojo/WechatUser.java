package com.meeting.meetresv.pojo;


public class WechatUser {
    private Integer id;

    private String wechatId;

    private Integer userId;

    public WechatUser(String wechatId, Integer userId) {
        this.wechatId = wechatId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}