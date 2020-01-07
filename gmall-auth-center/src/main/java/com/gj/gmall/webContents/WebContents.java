package com.gj.gmall.webContents;

import com.gj.gmall.utils.MD5Util;

/**
 *  关于验证的所有密钥
 *
 *  验证时加密
 *      1.key为md5Key (md5Key = "19950704,gongjin666")
 *      3.数据是用户的信息UmsMember的ID和昵称
 *      2.盐值是用户的真实IP加“:user:userId”的格式（如: 192.168.43.254:FA19C450FE8129143D920F447D321659）
 *      { saltofgongjin666sichuanshengguanganshi = FA19C450FE8129143D920F447D321659 }
 */
public class WebContents {

    // MD5盐值加密的密钥key
    public static final String md5Key = "7E54DCEA5B0168997FC40D87EB2023CF";

    /**
     * QQ第三方登录
     */
    // 获取Authorization Code
    public static final String QQRedirect_URI = "http://auth.gmall.com:10086/third/thirdlogin";
    public static final String QQAPPID = "appid";
    public static final String QQAPPKEY = "appkey";
    // 获取Authorization Code     GET
    public static String QQAuthorization_Code =
            "https://graph.qq.com/oauth2.0/authorize?client_id=CLIENT_ID&response_type=code&redirect_uri=REDIRECT_URI&state=gjlogin";
    // 通过Authorization Code获取Access Token       GET
    public static String QQAccess_Token =
            "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&code=CODE&redirect_uri=REDIRECT_URI";
    // （可选）权限自动续期，获取Access Token        GET
    public static String QQRefresh_Token =
            "https://graph.qq.com/oauth2.0/token?grant_type=refresh_token&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&refresh_token=REFRESH_TOKEN";
    // 获取用户OpenID        GET
    public static String QQOpenID =
            "https://graph.qq.com/oauth2.0/me?access_token=ACCESS_TOKEN";
    // 通过调用OpenAPI来获取或修改用户个人信息        GET
    public static String QQUser_Info =
            "https://graph.qq.com/user/get_user_info?access_token=ACCESS_TOKEN&oauth_consumer_key=APPID&openid=OPENID";

}
