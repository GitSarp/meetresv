package com.meeting.meetresv.controller;


import javax.servlet.http.HttpServletRequest;

public class BaseController {
    Boolean checkLogin(HttpServletRequest request){
            return request.getSession().getAttribute("user")!=null?true:false;
    }

    protected String doResult(int code){
        return code==1 ? "success":"error";
    }
}
