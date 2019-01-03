package com.meeting.meetresv.pojo;

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

    //使用此参数将string转换为date
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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