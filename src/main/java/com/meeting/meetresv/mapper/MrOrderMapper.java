package com.meeting.meetresv.mapper;

import com.meeting.meetresv.pojo.MrOrder;
import com.meeting.meetresv.pojo.MrOrderExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MrOrderMapper {
    long countByExample(MrOrderExample example);

    int deleteByExample(MrOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MrOrder record);

    int insertSelective(MrOrder record);

    List<MrOrder> selectByExample(MrOrderExample example);

    MrOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MrOrder record, @Param("example") MrOrderExample example);

    int updateByExample(@Param("record") MrOrder record, @Param("example") MrOrderExample example);

    int updateByPrimaryKeySelective(MrOrder record);

    int updateByPrimaryKey(MrOrder record);

    List<MrOrder> selectPrimitive(MrOrder order);
}