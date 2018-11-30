package com.meeting.meetresv.controller;


import javax.servlet.http.HttpServletRequest;

public class BaseController {
    Boolean checkLogin(HttpServletRequest request){
            //一些公共接口admin也会调用
            if((request.getSession().getAttribute("user")==null)&&(request.getSession().getAttribute("admin")==null)){
                return false;
            }
            return true;
    }

    Boolean checkAdminLogin(HttpServletRequest request){
        return request.getSession().getAttribute("admin")!=null?true:false;
    }

    protected String doResult(int code){
        return code==1 ? "success":"error";
    }
}
