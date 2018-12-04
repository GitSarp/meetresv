package com.meeting.meetresv.service.impl;

import com.meeting.meetresv.mapper.MrMeetingroomMapper;
import com.meeting.meetresv.pojo.MrMeetingroom;
import com.meeting.meetresv.pojo.MrMeetingroomExample;
import com.meeting.meetresv.service.RoomService;
import com.meeting.meetresv.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
    @Autowired
    MrMeetingroomMapper mrMeetingroomMapper;

    @Override
    public List<MrMeetingroom> getAllRoom(String roomNo){
        MrMeetingroomExample example=new MrMeetingroomExample();
        if(!StringUtil.isEmpty(roomNo)){
            example.createCriteria().andRoomNoEqualTo(roomNo);
        }
        return mrMeetingroomMapper.selectByExample(example);
    }

    @Override
    public int addRoom(MrMeetingroom record) {
        return mrMeetingroomMapper.insert(record);
    }

    @Override
    public int updateRoom(MrMeetingroom record) {
        return mrMeetingroomMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delRoom(Integer id) {
        return mrMeetingroomMapper.deleteByPrimaryKey(id);
    }
}
