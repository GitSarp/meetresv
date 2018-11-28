package com.meeting.meetresv.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CusResult {
    private String result;
    private String desc;

    public CusResult() {
    }

    public CusResult(String result, String desc) {
        this.result = result;
        this.desc = desc;
    }
}
