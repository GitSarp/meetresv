package com.meeting.meetresv.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    //日期设成String，WTF。。。
    //工作日入库
    public static String inDayStr(String str){
        String mouth=str.substring(0,str.indexOf('月'));
        String day= str.substring(str.indexOf('月')+1,str.indexOf('日'));
        if(Integer.parseInt(mouth)<10){
            mouth="0"+mouth;
        }
        if(Integer.parseInt(day)<10){
            day="0"+day;
        }
        return mouth+"月"+day+"日";
    }

    //工作日出库
    public static String outDayStr(String str){
        if(str.charAt(3)=='0'){
            str=str.substring(0,str.indexOf('月')+1)+str.substring(4);
        }
        if(str.charAt(0)=='0'){
            str=str.substring(1);
        }
        return str;
    }

    public static void main(String[] args) {
//        System.out.println(inDayStr("2月2日"));
//        System.out.println(outDayStr("02月02日"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(new Date());
        System.out.println(startTime);
    }

}
