package com.meeting.meetresv.pojo.page;

import com.meeting.meetresv.pojo.MrUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPage extends MrUser {
    int limit;
    int offset;
}
