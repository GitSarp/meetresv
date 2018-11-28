package com.meeting.meetresv.controller;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    String add(MrUser user,Model model){
        int result=userService.insert(user);
        if(result==0){
            model.addAttribute("result","ok");
        }else {
            model.addAttribute("result","failed");
        }
        return "success";
    }

    String delete(Integer id,Model model){
        int result= userService.deleteByPrimaryKey(id);
        if(result==0){
            model.addAttribute("result","ok");
        }else {
            model.addAttribute("result","failed");
        }
        return "success";
    }


    String update(MrUser user,Model model){
        int result= userService.updateByPrimaryKey(user);
        if(result==0){
            model.addAttribute("result","ok");
        }else {
            model.addAttribute("result","failed");
        }
        return "success";
    }

    String select(MrUserExample userExample, Map<String,String> param, Model model){
        List<MrUser> userList= userService.selectByExample(userExample);
        model.addAttribute("userList",userList);
        return "success";
    }

}
