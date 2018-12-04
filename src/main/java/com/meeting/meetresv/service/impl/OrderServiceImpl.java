package com.meeting.meetresv.service.impl;

import com.meeting.meetresv.mapper.MrOrderMapper;
import com.meeting.meetresv.pojo.MrOrder;
import com.meeting.meetresv.pojo.MrOrderExample;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.page.OrderPage;
import com.meeting.meetresv.service.OrderService;
import com.meeting.meetresv.utils.StringUtil;
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
    public List<MrOrder> query(OrderPage order) {
        return mrOrderMapper.selectPrimitive(order);
    }

    @Override
    public long count(MrOrder order) {
        MrOrderExample example=new MrOrderExample();
        MrOrderExample.Criteria criteria=example.createCriteria();
        if(!StringUtil.isEmpty(order.getRoomNo())){
            criteria= criteria.andRoomNoEqualTo(order.getRoomNo());
        }
        if(!StringUtil.isEmpty(order.getUser())){
            criteria= criteria.andUserEqualTo(order.getUser());
        }
        if(!StringUtil.isEmpty(order.getDay())){
            criteria= criteria.andDayEqualTo(order.getDay());
        }
        return mrOrderMapper.countByExample(example);
    }

    @Override
    public int cancelOrder(Integer id) {
        return mrOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateOrder(MrOrder order) {
        return mrOrderMapper.updateByPrimaryKey(order);
    }
}
