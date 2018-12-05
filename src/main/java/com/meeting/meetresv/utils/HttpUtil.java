package com.meeting.meetresv.utils;

import com.meeting.meetresv.utils.converter.WxMappingJackson2HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static RestTemplate restTemplate = new RestTemplate();
    //设置contentType
    static {
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
    }

    public static Map doReq1(String url, Map<String,String> params){
        Map result=null;
        try {
             result=restTemplate.getForObject(url, Map.class,params);
        }catch (RestClientException e){
            logger.error("小程序登录请求异常："+e.getMessage());
        }
        return result;
    }

    public static Map doReq2(String url){
        Map result=null;
        try {
            result=restTemplate.getForObject(url, Map.class);
        }catch (RestClientException e){
            logger.error("小程序登录请求异常："+e.getMessage());
        }
        return result;
    }

    public static Object doReq(String url, Map<String,Object> map,Class clazz){
        return restTemplate.getForObject(url, clazz,map);
    }

//    public static void main(String[] args) {
//        String  login_url="https://api.weixin.qq.com/sns/jscode2session";
//        String  appid="wx05ca89971b4a868b";
//        String  secret="28bb8ff8894cffa7f26c07929fb9fade";
//        String  grant_type="authorization_code";
//        Map<String,String> params=new HashMap<>();
//        params.put("appid",appid);
//        params.put("secret",secret);
//        params.put("grant_type",grant_type);
//        params.put("js_code","081UiDFV0lcGN121SeEV01eNFV0UiDF9");
//
//        Map testResp=restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=wx05ca89971b4a868b&secret=28bb8ff8894cffa7f26c07929fb9fade&js_code=081UiDFV0lcGN121SeEV01eNFV0UiDF9&grant_type=authorization_code",Map.class);
////        Map testResp=restTemplate.getForObject(login_url,Map.class,params);
//        System.out.println("debug");
//    }
}
