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

    public static void main(String[] args) {
        System.out.println(MD5Util.MD5EncodeUtf8("saltofgongjin666sichuanshengguanganshi"));
    }

}
