package com.meeting.meetresv.controller;

import com.meeting.meetresv.common.CusResult;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.service.UserService;
import com.meeting.meetresv.utils.EncryptUtil;
import com.meeting.meetresv.utils.ReadUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import org.apache.log4j.Logger;

@Api(value = "UserController",description = "用户相关api")
@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    private static final Logger logger= Logger.getLogger(UserController.class);

    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/login")
    public CusResult login(HttpServletRequest request,MrUser user){
        if(checkLogin(request)){
            return new CusResult("error","无需重复登录！");
        }
        MrUserExample userExample=new MrUserExample();
        userExample.createCriteria().andNameEqualTo(user.getName());
        List<MrUser> users=select(userExample);
        for(MrUser usr:users){
            if(EncryptUtil.md5Password(user.getPassword()).equals(usr.getPassword())){
                request.getSession().setAttribute("user",usr);
                return new CusResult("success","登录成功！");
            }
        }
        return new CusResult("error","账号或密码错误，请联系管理员！");
    }

    @ApiOperation(value="用户注销", notes="用户注销")
    @GetMapping("/logout")
    public CusResult logout(HttpServletRequest request){
        if(!checkLogin(request)){
            return new CusResult("error","您尚未登录！");
        }
        request.getSession().removeAttribute("user");
        return new CusResult("success","注销成功！");
    }

    @ApiOperation(value="用户改密", notes="用户改密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newPasswd", value = "新密码", required = true, dataType = "String")
    })
    @PostMapping("/modifyPD")
    public CusResult modifyPassword(HttpServletRequest request,String newPasswd){
        if(!checkLogin(request)){
            return new CusResult("error","您尚未登录！");
        }
        MrUser user=(MrUser)request.getSession().getAttribute("user");
        user.setPassword(newPasswd);
        EncryptUtil.encrypt(user);
        if(userService.updateByPrimaryKey(user)==1){
            request.getSession().setAttribute("user",user);
            return new CusResult("success","您的密码修改成功！");
        }
        return new CusResult("error","密码修改失败");
    }

    List<MrUser> select(MrUserExample userExample){
        List<MrUser> userList= userService.selectByExample(userExample);
        return userList;
    }
}
