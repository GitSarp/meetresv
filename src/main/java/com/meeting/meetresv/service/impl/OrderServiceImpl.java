package com.meeting.meetresv.service.impl;

import com.meeting.meetresv.mapper.MrOrderMapper;
import com.meeting.meetresv.pojo.MrOrder;
import com.meeting.meetresv.pojo.MrOrderExample;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    MrOrderMapper mrOrderMapper;

    @Override
    public int orderRoom(MrOrder order) {
        return mrOrderMapper.insert(order);
    }

    @Override
    public List<MrOrder> query(MrOrder order) {
        return mrOrderMapper.selectPrimitive(order);
    }

    @Override
    public int cancelOrder(Integer id) {
        return mrOrderMapper.deleteByPrimaryKey(id);
    }
}
