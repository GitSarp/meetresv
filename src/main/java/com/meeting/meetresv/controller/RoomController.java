package com.meeting.meetresv.controller;

import com.meeting.meetresv.common.CusResult;
import com.meeting.meetresv.pojo.MrMeetingroom;
import com.meeting.meetresv.service.RoomService;
import com.meeting.meetresv.utils.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "RoomController",description = "会议室相关api")
@RestController
@RequestMapping("/rooms")
public class RoomController extends BaseController {
    @Autowired
    RoomService roomService;

    @ApiOperation(value="查询会议室", notes="获取会议室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomNo", value = "会议室编号",  dataType = "String")
    })
    @GetMapping("/getRooms")
    List<MrMeetingroom> getAllRoom(String roomNo){
        return roomService.getAllRoom(roomNo);
    }

    @ApiOperation(value="新增会议室", notes="增加会议室")
    @PostMapping("/add")
    CusResult addRoom(HttpServletRequest request,MrMeetingroom room){
        if(!checkAdminLogin(request)){
            return new CusResult("error","请先登录！");
        }
        try{
            return new CusResult(doResult(roomService.addRoom(room)),"");
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            return new CusResult("error","会议室重复");
        }
    }

    @ApiOperation(value="更新会议室", notes="更新会议室")
    @PostMapping("/update")
    CusResult updateRoom(HttpServletRequest request,MrMeetingroom room){
        if(!checkAdminLogin(request)){
            return new CusResult("error","请先登录！");
        }
        return new CusResult(doResult(roomService.updateRoom(room)),"");
    }

    @ApiOperation(value="删除会议室", notes="删除会议室")
    @DeleteMapping("/del")
    CusResult deleteRoom(HttpServletRequest request,Integer id){
        if(!checkAdminLogin(request)){
            return new CusResult("error","请先登录！");
        }
        return new CusResult(doResult(roomService.delRoom(id)),"");
    }

}
