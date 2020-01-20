package com.gj.gmall.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//注解为配置类
@Component
public class WechatMPConfig {

    /*公众号appid*/
    private final static String mpAppId = "wxd4a7cd624e9938ca";
    /*公众号secret*/
    private final static String mpAppSecret  = "18f7c9a9df08860f055aa601c3cc674d";

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());

        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        //在这里我们要设置appid 和 appsecret 需要在配置文件里面设置两个变量，这样全局都可以用
        //然后设置一个WechatAccountConfig类，来注入这两个参数，这样在使用的时候就可以直接调用这两个类
        wxMpConfigStorage.setAppId(mpAppId);
        wxMpConfigStorage.setSecret(mpAppSecret );

        return  wxMpConfigStorage;
    }

}
