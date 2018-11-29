package com.meeting.meetresv.controller;

import com.meeting.meetresv.pojo.MrMeetingroom;
import com.meeting.meetresv.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController extends BaseController {
    @Autowired
    RoomService roomService;

    @GetMapping("/rooms")
    List<MrMeetingroom> getAllRoom(){
        return roomService.getAllRoom();
    }
}
