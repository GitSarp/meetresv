package com.meeting.meetresv.service;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface UserService {

    int insert(MrUser record) throws DuplicateKeyException;

    int insertSelective(MrUser record);

    int deleteByExample(MrUserExample example);

    int deleteByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MrUser record, @Param("example") MrUserExample example);

    int updateByExample(@Param("record") MrUser record, @Param("example") MrUserExample example);

    int updateByPrimaryKeySelective(MrUser record);

    int updateByPrimaryKey(MrUser record);

    List<MrUser> selectByExample(MrUserExample example);

    MrUser selectByPrimaryKey(Integer id);
}
