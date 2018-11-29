package com.meeting.meetresv.pojo;

public class MrMeetingroom {
    private Integer id;

    private String roomNo;

    private String mrExt;

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

    public String getMrExt() {
        return mrExt;
    }

    public void setMrExt(String mrExt) {
        this.mrExt = mrExt == null ? null : mrExt.trim();
    }
}