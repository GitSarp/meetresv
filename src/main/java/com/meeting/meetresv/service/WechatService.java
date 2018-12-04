package com.meeting.meetresv.service;

import com.meeting.meetresv.pojo.WechatUser;
import com.meeting.meetresv.pojo.WechatUserExample;

import java.util.List;

public interface WechatService {

    List<WechatUser> validate(String wechat_no);
    int deleteByUserId(int id);
    int insert(WechatUser record);
}
