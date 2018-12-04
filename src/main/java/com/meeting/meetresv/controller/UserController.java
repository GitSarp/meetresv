package com.meeting.meetresv.controller;

import com.meeting.meetresv.common.CusResult;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.pojo.WechatUser;
import com.meeting.meetresv.service.UserService;
import com.meeting.meetresv.service.WechatService;
import com.meeting.meetresv.utils.EncryptUtil;
import com.meeting.meetresv.utils.ReadUserUtil;
import com.meeting.meetresv.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@Api(value = "UserController",description = "用户相关api")
@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    WechatService wechatService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

//    private static final Logger logger= Logger.getLogger(UserController.class);

    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wechat_no", value = "微信标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名",  dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String")
    })
    @PostMapping("/login")
    public CusResult login(HttpServletRequest request,MrUser user,String wechat_no){
//        if(checkLogin(request)){
//            return new CusResult("error","无需重复登录！");
//        }
        if(StringUtil.isEmpty(wechat_no)){
            return new CusResult("error","必须传入微信标识！");
        }

        if(wechatService.validate(wechat_no).size()>0){
            request.getSession().setAttribute("user",user);
            return new CusResult("success","成功！");
        }else{
            if(user.getName()!=null){
                MrUserExample userExample=new MrUserExample();
                userExample.createCriteria().andNameEqualTo(user.getName());
                List<MrUser> users=select(userExample);
                for(MrUser usr:users){
                    if(EncryptUtil.md5Password(user.getPassword()).equals(usr.getPassword())){
                        //插入绑定
                        if(wechatService.insert(new WechatUser(wechat_no,usr.getId()))==1){
                            request.getSession().setAttribute("user",usr);
                            return new CusResult("success","成功！");
                        }else{
                            return new CusResult("error","系统内部错误！");
                        }
                    }
                }
                return new CusResult("error","账号或密码错误，请联系管理员！");
            }else{
                return new CusResult("info","首次请登录！");
            }
        }
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
