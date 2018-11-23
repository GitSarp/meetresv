package com.meeting.meetresv.service;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    long countByExample(MrUserExample example);

    int deleteByExample(MrUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MrUser record);

    int insertSelective(MrUser record);

    List<MrUser> selectByExample(MrUserExample example);

    MrUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MrUser record, @Param("example") MrUserExample example);

    int updateByExample(@Param("record") MrUser record, @Param("example") MrUserExample example);

    int updateByPrimaryKeySelective(MrUser record);

    int updateByPrimaryKey(MrUser record);
}
