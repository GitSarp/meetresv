package com.meeting.meetresv.service;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.pojo.page.UserPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface UserService {

    int insert(MrUser record) throws DuplicateKeyException;

    List<MrUser> query(UserPage user);
    long count(MrUser order);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(MrUser record);

    List<MrUser> selectByExample(MrUserExample example);

}
