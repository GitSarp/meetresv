package com.meeting.meetresv.mapper;

import com.meeting.meetresv.pojo.MrMeetingroom;
import com.meeting.meetresv.pojo.MrMeetingroomExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MrMeetingroomMapper {
    long countByExample(MrMeetingroomExample example);

    int deleteByExample(MrMeetingroomExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MrMeetingroom record);

    int insertSelective(MrMeetingroom record);

    List<MrMeetingroom> selectByExample(MrMeetingroomExample example);

    MrMeetingroom selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MrMeetingroom record, @Param("example") MrMeetingroomExample example);

    int updateByExample(@Param("record") MrMeetingroom record, @Param("example") MrMeetingroomExample example);

    int updateByPrimaryKeySelective(MrMeetingroom record);

    int updateByPrimaryKey(MrMeetingroom record);
}