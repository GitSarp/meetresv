package com.meeting.meetresv.service.impl;

import com.meeting.meetresv.mapper.MrUserMapper;
import com.meeting.meetresv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    MrUserMapper mrUserMapper;

    
}
