package com.meeting.meetresv.service;

import com.meeting.meetresv.pojo.MrOrder;

import java.util.List;
import java.util.Map;

public interface OrderService {

    int orderRoom(MrOrder order);
    List<MrOrder> query(MrOrder order);
    int cancelOrder(Integer id);
}
