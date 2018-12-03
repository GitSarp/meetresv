package com.meeting.meetresv.pojo.page;

import com.meeting.meetresv.pojo.MrOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPage extends MrOrder {
    int limit;
    int offset;
}
