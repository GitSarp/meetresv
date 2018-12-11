package com.meeting.meetresv.controller;

import com.meeting.meetresv.common.CusResult;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.pojo.WechatUser;
import com.meeting.meetresv.service.UserService;
import com.meeting.meetresv.service.WechatService;
import com.meeting.meetresv.utils.EncryptUtil;
import com.meeting.meetresv.utils.HttpUtil;
import com.meeting.meetresv.utils.StringUtil;
import com.meeting.meetresv.utils.redis.RedisUtil;
import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "UserController",description = "用户相关api")
@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    WechatService wechatService;

    @Autowired
    RedisUtil redisUtil;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    //小程序登录参数
//    private static final String  login_url="https://api.weixin.qq.com/sns/jscode2session";
//    private static final String  appid="wx05ca89971b4a868b";
//    private static final String  secret="28bb8ff8894cffa7f26c07929fb9fade";
//    private static final String  grant_type="authorization_code";

    private static String url="https://api.weixin.qq.com/sns/jscode2session?appid=wx05ca89971b4a868b&secret=28bb8ff8894cffa7f26c07929fb9fade&grant_type=authorization_code&js_code=";

    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "js_code", value = "微信登录标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名",  dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String")
    })
    @PostMapping("/login")
    public CusResult login(HttpServletRequest request,MrUser user,String js_code){
//        if(checkLogin(skey)){
//            return new CusResult("error","无需重复登录！");
//        }
        if(StringUtil.isEmpty(js_code)){
            return new CusResult("error","必须传入js_code！");
        }

        //小程序登录
//        Map<String,String> params=new HashMap<>();
//        params.put("appid",appid);
//        params.put("secret",secret);
//        params.put("grant_type",grant_type);
//        params.put("js_code",js_code);
//        Map result=HttpUtil.doReq1(login_url,params);

        Map result=HttpUtil.doReq2(url+js_code);

        Integer errorCode=(Integer) result.get("errcode");
        String errorMsg=(String)result.get("errmsg");
        String openId=(String)result.get("openid");
        String sessionKey=(String)result.get("session_key");

        logger.info("小程序接口返回:\n errcode:"+errorCode+";errmsg"+errorMsg+"openId:"+openId+";sessionKey"+sessionKey);

        if((errorCode!=null)&&(errorCode!=0)){
            return new CusResult("error","小程序登录失败,错误信息："+errorCode+";"+errorMsg);
        }else{
            //登录成功
            List<WechatUser> bindList=wechatService.validate(openId);
            if((bindList!=null)&&(bindList.size()>0)){
                MrUserExample userExample=new MrUserExample();
                userExample.createCriteria().andIdEqualTo(bindList.get(0).getUserId());
                List<MrUser> users=select(userExample);
                if(users.size()<=0){
                    return new CusResult("error","无此用户，请联系管理员！");
                }
                redisUtil.add(sessionKey,30L,users.get(0));
                return new CusResult("success","登录成功!",sessionKey);
            }else{
                //首次绑定
                if(user.getName()!=null){
                    MrUserExample userExample=new MrUserExample();
                    userExample.createCriteria().andNameEqualTo(user.getName());
                    List<MrUser> users=select(userExample);
                    for(MrUser usr:users){
                        if(EncryptUtil.md5Password(user.getPassword()).equals(usr.getPassword())){
                            //插入绑定
                            if(wechatService.insert(new WechatUser(openId,usr.getId()))==1){
                                redisUtil.add(sessionKey,30L,user);
                                return new CusResult("success","登录成功!",sessionKey);
                            }else{
                                return new CusResult("error","预约系统错误！");
                            }
                        }
                    }
                    return new CusResult("error","绑定账号失败，账号或密码错误！");
                }else {
                    return new CusResult("info","首次请登录！");
                }
            }
        }
    }

    @ApiOperation(value="用户注销", notes="用户注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skey", value = "sessionKey",  dataType = "String",required = true)
    })
    @GetMapping("/logout")
    public CusResult logout(HttpServletRequest request,String skey){
        if(checkLogin(request,skey)==null){
            return new CusResult("error","您尚未登录！");
        }
        redisUtil.delete(skey);
        return new CusResult("success","注销成功！");
    }

    @ApiOperation(value="用户改密", notes="用户改密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newPasswd", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "skey", value = "sessionKey",  dataType = "String",required = true)
    })
    @PostMapping("/modifyPD")
    public CusResult modifyPassword(HttpServletRequest request,String newPasswd,String skey){
        if(checkLogin(request,skey)==null){
            return new CusResult("error","请先登录！");
        }
        MrUser user=redisUtil.get(skey);
        user.setPassword(newPasswd);
        EncryptUtil.encrypt(user);
        if(userService.updateByPrimaryKey(user)==1){
            redisUtil.add(skey,30L,user);
            return new CusResult("success","您的密码修改成功！");
        }
        return new CusResult("error","密码修改失败");
    }

    List<MrUser> select(MrUserExample userExample){
        List<MrUser> userList= userService.selectByExample(userExample);
        return userList;
    }
}
