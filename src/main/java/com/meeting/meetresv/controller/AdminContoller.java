package com.meeting.meetresv.controller;

import com.meeting.meetresv.common.CusResult;
import com.meeting.meetresv.pojo.MrMeetingroom;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.pojo.page.UserPage;
import com.meeting.meetresv.service.UserService;
import com.meeting.meetresv.utils.EncryptUtil;
import com.meeting.meetresv.utils.ReadUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "AdminContoller",description = "管理员相关api")
@Controller
@RequestMapping("/admin")
public class AdminContoller extends BaseController {

    @Autowired
    UserService userService;

    private static final Logger logger= Logger.getLogger(UserController.class);

    @GetMapping("/login")
    public String adminLogin(){
        return "login";
    }

    @ApiOperation(value="管理员登录", notes="管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/logindo")
    public String login(HttpServletRequest request,MrUser user,Model model){
        if(checkAdminLogin(request)){
            model.addAttribute("msg","无需重复登录！");
            return "index";
        }
        MrUserExample userExample=new MrUserExample();
        userExample.createCriteria().andNameEqualTo(user.getName()).andRoleEqualTo(true);
        List<MrUser> users=select(userExample);
        for(MrUser usr:users){
            if(EncryptUtil.md5Password(user.getPassword()).equals(usr.getPassword())){
                request.getSession().setAttribute("admin",usr);
                model.addAttribute("msg","登录成功！");
                return "index";
            }
        }
        model.addAttribute("msg","账号或密码错误，请联系管理员！");
        return "login";
    }

    @ApiOperation(value="用户注销", notes="用户注销")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,Model model){
        if(!checkAdminLogin(request)){
            model.addAttribute("msg","您尚未登录！");
            return "login";
        }
        request.getSession().removeAttribute("admin");
        model.addAttribute("msg","注销成功！");
        return "login";
    }

    @GetMapping("/modifyPD")
    public String modify(){
        return "modifyPD";
    }

    @ApiOperation(value="用户改密", notes="用户改密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newPasswd", value = "新密码", required = true, dataType = "String")
    })
    @PostMapping("/modifyPD")
    public String modifyPassword(HttpServletRequest request,String newPasswd,String oldPasswd,Model model){
        if(!checkAdminLogin(request)){
            model.addAttribute("msg","您尚未登录！");
            return "login";
        }
        MrUser user=(MrUser)request.getSession().getAttribute("admin");
        if(!EncryptUtil.md5Password(oldPasswd).equals(user.getPassword())){
            model.addAttribute("msg","旧密码错误！");
            return "modifyPD";
        }
        user.setPassword(newPasswd);
        EncryptUtil.encrypt(user);
        if(userService.updateByPrimaryKey(user)==1){
            request.getSession().removeAttribute("admin");
            model.addAttribute("msg","您的密码修改成功,请重新登录！");
            return "modifyPD";
        }
        model.addAttribute("msg","密码修改失败！");
        return "modifyPD";
    }


    @GetMapping("/users")
    public String userManage(){
        return "userManage";
    }
    @GetMapping("/rooms")
    public String roomManage(){
        return "roomManage";
    }
    @GetMapping("/orders")
    String orderManage(){
        return "orderManage";
    }
    @GetMapping("/batch")
    public String batchDo(){
        return "batch";
    }

    //批量导入excel用户
    @PostMapping("/uploadUser")
    public String upload(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) {
        if(!checkAdminLogin(request)){
            model.addAttribute("msg","请先登录！");
            return "login";
        }
        if (!file.isEmpty()) {
            String fileName=file.getOriginalFilename();//原始文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if (!suffixName.toLowerCase().endsWith("xlsx")&&!suffixName.toLowerCase().endsWith("xls")) {
                model.addAttribute("msg","请选择有效的excel文件！");
                return "batch";
            }
            String destName="user"+suffixName;//转换文件名
            File dest=new File(destName);

            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(dest));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                model.addAttribute("msg","文件上传失败！"+e.getMessage());
                return "batch";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("msg","文件上传失败！"+e.getMessage());
                return "batch";
            }

            try {
                ReadUserUtil.readExcel(destName,userService);
                if(!dest.delete()){
                    logger.info("删除用户excel失败...");
                }
                model.addAttribute("msg","批量导入用户成功！");
                return "batch";
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("批量导入用户失败...");
                model.addAttribute("msg","批量导入用户失败！"+e.getMessage());
                return "batch";
            }
        } else {
            model.addAttribute("msg","上传失败，因为文件是空的！");
            return "batch";
        }
    }


    List<MrUser> select(MrUserExample userExample){
        List<MrUser> userList= userService.selectByExample(userExample);
        return userList;
    }

    @PostMapping("/add")
    @ResponseBody CusResult add(MrUser user, Model model){
        try{
            EncryptUtil.encrypt(user);
            return new CusResult(doResult(userService.insert(user)),"");
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            return new CusResult("error","用户名重复");
        }
    }

    @DeleteMapping("/del")
    @ResponseBody  CusResult delete(Integer id,Model model){
        return new CusResult(doResult(userService.deleteByPrimaryKey(id)),"");
    }


    @PostMapping("/update")
    @ResponseBody CusResult update(MrUser user,Model model){
        EncryptUtil.encrypt(user);
        return new CusResult(doResult(userService.updateByPrimaryKey(user)),"");
    }

    @ApiOperation(value="用户查询", notes="查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String"),
            @ApiImplicitParam(name = "department", value = "部门", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "int"),
            @ApiImplicitParam(name = "offset", value = "页码", dataType = "int")})
    @GetMapping("/getUsers")
    @ResponseBody Map<String,Object> getUsers(UserPage userPage){
        Map result=new HashMap();
        if(userPage.getLimit()!=0){
            result.put("page",userPage.getOffset()/userPage.getLimit()+1);
        }
        result.put("rows",userService.query(userPage));
        result.put("total",userService.count(userPage));
        userPage.setOffset((userPage.getOffset()-1)*userPage.getLimit());
        return result;
    }
}
