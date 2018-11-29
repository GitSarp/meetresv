package com.meeting.meetresv.controller;

import com.meeting.meetresv.common.CusResult;
import com.meeting.meetresv.pojo.MrOrder;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.service.OrderService;
import com.meeting.meetresv.utils.DateUtil;
import com.meeting.meetresv.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "OrderController",description = "预约相关api")
@RestController
public class OrderController extends BaseController{

    @Autowired
    OrderService orderService;

    @ApiOperation(value="预约会议室", notes="增加预约信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomNo", value = "会议室编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "day", value = "工作日，不传默认今天", dataType = "String"),
            @ApiImplicitParam(name = "period", value = "占用时段", required = true, dataType = "String"),
            @ApiImplicitParam(name = "purpose", value = "用途",  dataType = "String")
    })

    @PostMapping("/order")
    public CusResult orderRoom(HttpServletRequest request,MrOrder order){
        if(!checkLogin(request)){
            return new CusResult("error","请先登录！");
        }
        if(StringUtil.isEmpty(order.getDay())){
            order.setDay(DateUtil.getWeek(new Date()));
        }
        order.setUser(((MrUser)request.getSession().getAttribute("user")).getName());
        return new CusResult(doResult(orderService.orderRoom(order)),"");
    }

    @ApiOperation(value="预约查询", notes="查询预约信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomNo", value = "会议室编号", dataType = "String"),
            @ApiImplicitParam(name = "user", value = "预约者姓名", dataType = "String"),
            @ApiImplicitParam(name = "day", value = "工作日", dataType = "String"),
            @ApiImplicitParam(name = "purpose", value = "用途",  dataType = "String")})
    @PostMapping("/orderQuery")
    public List<MrOrder> query(MrOrder order){
        return orderService.query(order);
    }

    @ApiOperation(value="取消预约", notes="取消预约信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "预约id", required = true, dataType = "Integer")})
    @PostMapping("/cancel")
    public CusResult cancelOrder(HttpServletRequest request, Integer id){
        if(!checkLogin(request)){
            return new CusResult("error","请先登录！");
        }
        return new CusResult(doResult(orderService.cancelOrder(id)),"");
    }
}
