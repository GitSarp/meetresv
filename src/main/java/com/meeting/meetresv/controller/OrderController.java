package com.meeting.meetresv.controller;

import com.meeting.meetresv.common.CusResult;
import com.meeting.meetresv.pojo.MrOrder;
import com.meeting.meetresv.pojo.MrOrderExample;
import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.page.OrderPage;
import com.meeting.meetresv.service.OrderService;
import com.meeting.meetresv.utils.DateUtil;
import com.meeting.meetresv.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "OrderController",description = "预约相关api")
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    @Autowired
    OrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @ApiOperation(value="预约会议室", notes="增加预约信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomNo", value = "会议室编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "day", value = "工作日，不传默认今天", dataType = "String"),
            @ApiImplicitParam(name = "period", value = "占用时段", required = true, dataType = "String"),
            @ApiImplicitParam(name = "skey", value = "sessionKey",  dataType = "String"),
            @ApiImplicitParam(name = "purpose", value = "用途",  dataType = "String",required = true)
    })
    @PostMapping("/add")
    public CusResult orderRoom(HttpServletRequest request,MrOrder order,String skey){
        MrUser user=(MrUser) checkLogin(request,skey);
        if(user==null){
            logger.info("error:"+"请先登录!");
            return new CusResult("error","请先登录！");
        }

        if(order.getDay() == null){
            order.setDay(new Date());
        }
        //保存用户名
        String username="";
        if(order.getUser()!=null){
            username=order.getUser();
        }
        //并发控制
        //待校验时间
        String[] needCheckTime=StringUtil.divide(order.getPeriod());
        int result=0;
        synchronized (this){
            //获取已预约信息,校验预约时间冲突
            order.setUser("");//去除用户查询条件
            List<MrOrder> orders=orderService.query(new OrderPage(order));
            for (MrOrder tmp:orders) {
                String[] duration=StringUtil.divide(tmp.getPeriod());
                if((needCheckTime[1].compareTo(duration[0])<=0)||(needCheckTime[0].compareTo(duration[1])>=0)){
                    continue;
                }else{
                    logger.info("error:"+"预约时间冲突，请刷新后重试!");
                    return new CusResult("error","预约时间冲突，请刷新后重试");
                }
            }

            logger.info("info:"+"开始预约，请查看表数据");
            //获取用户名
            if(username.equals("")){
                order.setUser(user.getName());
            }else{
                order.setUser(username);
            }
            try {
                result=orderService.orderRoom(order);
            }catch (Exception e){
                return new CusResult("error","失败，请刷新数据后重试");
            }
        }
        return new CusResult(doResult(result),"");
    }

    @ApiOperation(value="预约查询", notes="查询预约信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomNo", value = "会议室编号", dataType = "String"),
            @ApiImplicitParam(name = "user", value = "预约者姓名", dataType = "String"),
            @ApiImplicitParam(name = "day", value = "工作日", dataType = "String"),
//            @ApiImplicitParam(name = "purpose", value = "用途",  dataType = "String"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "int"),
            @ApiImplicitParam(name = "offset", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "skey", value = "sessionKey",  dataType = "String")})
    @RequestMapping(value = "/query",method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String,Object> pageQuery(OrderPage order,String skey,HttpServletRequest request){
        if(!StringUtil.isEmpty(skey)){
            order.setUser(redisUtil.get(skey).getName());
        }
        Map result=new HashMap();
        if(order.getLimit()!=0){
            result.put("page",order.getOffset()/order.getLimit()+1);
        }
        List<MrOrder> list=orderService.query(order);
        result.put("rows",list);
        result.put("total",orderService.count(order));
        order.setOffset((order.getOffset()-1)*order.getLimit());
        return result;
    }

    @ApiOperation(value="取消预约", notes="取消预约信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "预约id", required = true, dataType = "Integer"),
                        @ApiImplicitParam(name = "skey", value = "sessionKey",  dataType = "String")})
    @PostMapping("/del")
    public CusResult cancelOrder(HttpServletRequest request, Integer id,String skey){
        if(checkLogin(request,skey)==null){
            return new CusResult("error","请先登录！");
        }
        return new CusResult(doResult(orderService.cancelOrder(id)),"");
    }

    @ApiOperation(value="更新预约信息", notes="更新预约信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomNo", value = "会议室编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "day", value = "工作日，不传默认今天", dataType = "String"),
            @ApiImplicitParam(name = "user", value = "预约人", dataType = "String"),
            @ApiImplicitParam(name = "period", value = "占用时段", required = true, dataType = "String"),
            @ApiImplicitParam(name = "purpose", value = "用途",  dataType = "String"),
            @ApiImplicitParam(name = "skey", value = "sessionKey",  dataType = "String")
    })
    @PostMapping("/update")
    CusResult updateRoom(HttpServletRequest request,MrOrder order,String skey){
        if(checkLogin(request,skey)==null){
            return new CusResult("error","请先登录！");
        }
        //并发控制
        //待校验时间
        String[] needCheckTime=StringUtil.divide(order.getPeriod());
        //保存用户名
        String username=order.getUser();
        synchronized (this){
            order.setUser("");//去除用户查询条件
            //获取已预约信息,校验预约时间冲突
            List<MrOrder> orders=orderService.query(new OrderPage(order));
            for (MrOrder tmp:orders) {
                //自我排除
                if(tmp.getId().equals(order.getId())){
                    continue;
                }
                String[] duration=StringUtil.divide(tmp.getPeriod());
                if((needCheckTime[1].compareTo(duration[0])<=0)||(needCheckTime[0].compareTo(duration[1])>=0)){
                    continue;
                }else{
                    return new CusResult("error","预约时间冲突，请刷新确认后重试");
                }
            }
            //获取用户名
            order.setUser(username);
            return new CusResult(doResult(orderService.updateOrder(order)),"");
        }
    }
}
