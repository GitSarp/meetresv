package com.meeting.meetresv.controller;


import com.meeting.meetresv.utils.StringUtil;
import com.meeting.meetresv.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    RedisUtil redisUtil;

    Boolean checkLogin(HttpServletRequest request, String skey) {
        //一些公共接口admin也会调用
        if (StringUtil.isEmpty(skey)) {
            return checkAdminLogin(request);
        } else {
            if (redisUtil.get(skey) == null) {
                return false;
            }
            return true;
        }
    }

    Boolean checkAdminLogin(HttpServletRequest request){
        return request.getSession().getAttribute("admin")!=null?true:false;
    }

    protected String doResult(int code){
        return code==1 ? "success":"error";
    }
}
