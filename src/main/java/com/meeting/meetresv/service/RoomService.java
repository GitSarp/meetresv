package com.meeting.meetresv.service;

import com.meeting.meetresv.pojo.MrMeetingroom;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RoomService {
    List<MrMeetingroom> getAllRoom(String roomNo);
    int addRoom(MrMeetingroom record);
    int updateRoom(MrMeetingroom record);
    int delRoom(Integer id);
}
