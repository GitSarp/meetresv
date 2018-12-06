package com.meeting.meetresv.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 工具类-字符串处理
 * @author xx
 * @version 1.0
 * @date 2015年11月17日 下午5:40:13
 * Copyright 杭州融都科技股份有限公司 统一支付系统 UPS  All Rights Reserved
 * 官方网站：www.erongdu.com
 * 研发中心：rdc@erongdu.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public class StringUtil extends StringUtils {

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstCharUpperCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}

	/**
	 * -拆分出两个字符串
	 *
	 * @param source
	 * @return
	 */
	public static String[] divide(String source){
		if(source.indexOf('-')<0){
			return new String[]{};
		}
		String first=source.substring(0,source.indexOf('-'));
		String next=source.substring(source.indexOf('-')+1);
		String[] result=new String[]{
				first,next
		};
		return result;
 	}
}
