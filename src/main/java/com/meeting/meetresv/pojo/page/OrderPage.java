package com.meeting.meetresv.pojo.page;

import com.meeting.meetresv.pojo.MrOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderPage extends MrOrder {
    int limit;
    int offset;

    public OrderPage(){

    }
    public OrderPage(String roomNo, String user, Date day){
        this.setRoomNo(roomNo);
        this.setUser(user);
        this.setDay(day);
    }

    public OrderPage(MrOrder order){
        this.setRoomNo(order.getRoomNo());
        this.setUser(order.getUser());
        this.setDay(order.getDay());
    }
}
