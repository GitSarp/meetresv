package com.meeting.meetresv.service.impl;

import com.meeting.meetresv.mapper.MrUserMapper;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.pojo.page.UserPage;
import com.meeting.meetresv.service.UserService;
import com.meeting.meetresv.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    MrUserMapper mrUserMapper;

    @Override
    public int insert(MrUser record) throws DuplicateKeyException {
        return mrUserMapper.insert(record);
    }
    

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mrUserMapper.deleteByPrimaryKey(id);
    }
    

    @Override
    public int updateByPrimaryKey(MrUser record) {
        return mrUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<MrUser> selectByExample(MrUserExample example) {
        return mrUserMapper.selectByExample(example);
    }

    @Override
    public List<MrUser> query(UserPage user) {
        return mrUserMapper.selectPrimitive(user);
    }

    @Override
    public long count(MrUser user) {
        MrUserExample example=new MrUserExample();
        MrUserExample.Criteria criteria=example.createCriteria();
        if(!StringUtil.isEmpty(user.getName())){
            criteria= criteria.andNameEqualTo(user.getName());
        }
        if(!StringUtil.isEmpty(user.getDepartment())){
            criteria= criteria.andDepartmentEqualTo(user.getDepartment());
        }
        if(!StringUtil.isEmpty(user.getPhone())){
            criteria= criteria.andPhoneEqualTo(user.getPhone());
        }
        if(user.getRole()!=null){
            criteria= criteria.andRoleEqualTo(user.getRole());
        }
        return mrUserMapper.countByExample(example);
    }
}
