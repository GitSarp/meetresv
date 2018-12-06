package com.meeting.meetresv.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CusResult {
    //为生成规范的api文档
    //result改为整型的code比较好
    private String result;
    //desc改成message
    private String desc;

    private String data;

    public CusResult() {
    }

    public CusResult(String result, String desc) {
        this(result,desc,"");
    }

    public CusResult(String result, String desc,String data) {
        this.data=data;
        this.result = result;
        this.desc = desc;
    }
}
