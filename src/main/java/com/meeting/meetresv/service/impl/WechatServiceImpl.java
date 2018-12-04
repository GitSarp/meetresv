package com.meeting.meetresv.service.impl;

import com.meeting.meetresv.mapper.WechatUserMapper;
import com.meeting.meetresv.pojo.WechatUser;
import com.meeting.meetresv.pojo.WechatUserExample;
import com.meeting.meetresv.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wechatService")
public class WechatServiceImpl implements WechatService {
    @Autowired
    WechatUserMapper wechatUserMapper;

    @Override
    public List<WechatUser> validate(String wechat_no) {
        WechatUserExample example=new WechatUserExample();
        example.createCriteria().andWechatIdEqualTo(wechat_no);
        return wechatUserMapper.selectByExample(example);
    }

    @Override
    public int deleteByUserId(int id) {
        WechatUserExample example=new WechatUserExample();
        example.createCriteria().andUserIdEqualTo(id);
        return wechatUserMapper.deleteByExample(example);
    }

    @Override
    public int insert(WechatUser record){
        return wechatUserMapper.insert(record);
    }
}
