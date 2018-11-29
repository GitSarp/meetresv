package com.meeting.meetresv.controller;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserCommonController extends BaseController {
    @Autowired
    UserService userService;

    List<MrUser> select(MrUserExample userExample){
        List<MrUser> userList= userService.selectByExample(userExample);
        return userList;
    }
}
