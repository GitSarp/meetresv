package com.meeting.meetresv.service;

import com.meeting.meetresv.pojo.MrOrder;
import com.meeting.meetresv.pojo.MrOrderExample;
import com.meeting.meetresv.pojo.page.OrderPage;

import java.util.List;
import java.util.Map;

public interface OrderService {

    int orderRoom(MrOrder order);
    List<MrOrder> query(OrderPage order);
    long count(MrOrder order);
    int cancelOrder(Integer id);
    int updateOrder(MrOrder order);
}
