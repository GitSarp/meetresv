package com.meeting.meetresv.service.impl;

import com.meeting.meetresv.mapper.MrUserMapper;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.service.UserService;
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
    public int insertSelective(MrUser record) {
        return mrUserMapper.insertSelective(record);
    }

    @Override
    public int deleteByExample(MrUserExample example) {
        return mrUserMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mrUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(MrUser record, MrUserExample example) {
        return mrUserMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(MrUser record, MrUserExample example) {
        return mrUserMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(MrUser record) {
        return mrUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MrUser record) {
        return mrUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MrUser> selectByExample(MrUserExample example) {
        return mrUserMapper.selectByExample(example);
    }

    @Override
    public MrUser selectByPrimaryKey(Integer id) {
        return mrUserMapper.selectByPrimaryKey(id);
    }
}
