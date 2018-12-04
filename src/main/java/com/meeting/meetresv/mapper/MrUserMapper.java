package com.meeting.meetresv.mapper;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

@Mapper
public interface MrUserMapper {
    long countByExample(MrUserExample example);

    int deleteByExample(MrUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MrUser record) throws DuplicateKeyException;

    int insertSelective(MrUser record);

    List<MrUser> selectByExample(MrUserExample example);

    MrUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MrUser record, @Param("example") MrUserExample example);

    int updateByExample(@Param("record") MrUser record, @Param("example") MrUserExample example);

    int updateByPrimaryKeySelective(MrUser record);

    int updateByPrimaryKey(MrUser record);

    List<MrUser> selectPrimitive(MrUser user);
}