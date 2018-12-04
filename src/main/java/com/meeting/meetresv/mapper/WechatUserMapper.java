package com.meeting.meetresv.mapper;

import com.meeting.meetresv.pojo.WechatUser;
import com.meeting.meetresv.pojo.WechatUserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WechatUserMapper {
    long countByExample(WechatUserExample example);

    int deleteByExample(WechatUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    List<WechatUser> selectByExample(WechatUserExample example);

    WechatUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatUser record, @Param("example") WechatUserExample example);

    int updateByExample(@Param("record") WechatUser record, @Param("example") WechatUserExample example);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);
}