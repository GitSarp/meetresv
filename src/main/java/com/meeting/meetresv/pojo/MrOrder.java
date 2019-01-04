package com.meeting.meetresv.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MrOrder {
    private Integer id;

    private String roomNo;

    private String user;

    private Date day;

    private String period;

    private String purpose;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo == null ? null : roomNo.trim();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /*
        以下日期形式兼容各种浏览器
        var d = new Date(2011, 01, 07); // yyyy, mm-1, dd
        var d = new Date(2011, 01, 07, 11, 05, 00); // yyyy, mm-1, dd, hh, mm, ss
        var d = new Date("02/07/2011"); // "mm/dd/yyyy"
        var d = new Date("02/07/2011 11:05:00"); // "mm/dd/yyyy hh:mm:ss"
        var d = new Date(1297076700000); // milliseconds
        var d = new Date("Mon Feb 07 2011 11:05:00 GMT"); // ""Day Mon dd yyyy hh:mm:ss GMT/UTC
    */

    //此注解将string参数绑定到date
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")//返回时转为json
    public Date getDay() {
        return day;
    }


    public void setDay(Date day) {
        this.day=day;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }
}