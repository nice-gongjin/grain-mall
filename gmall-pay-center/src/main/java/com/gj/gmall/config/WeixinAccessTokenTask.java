package com.gj.gmall.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gj.gmall.util.wechat.WeChatConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Component
public class WeixinAccessTokenTask {

//    @Autowired
//    private WeixinService weixinService;

    private Logger logger = LoggerFactory.getLogger(WeixinAccessTokenTask.class);

//    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    //定义获取token的接口地址
    private static final String ACCESS_TOKEN_URL = WeChatConstant.ACCESS_TOKEN_URL;
    //定义APPID和APPSECRET
    private static final String APPID = WechatConfig.APPID;
    private static final String APPSECRET = WechatConfig.APPSECRET;

    @Scheduled(initialDelay = 6 * 1000, fixedDelay = 24 * 60 * 60 * 1000 )
    public void orderStatus() {
        //调用推送消息的业务类处理请求
//        weixinService.pushMessge();
    }

    /**
     * access_token 是微信的全局唯一调用凭据
     * access_token 的有效期为 2 个小时，需要定时刷新 access_token，重复获取会导致之前一次获取的
     * access_token 失效
     * 延迟一秒执行
     */
    @Scheduled(initialDelay = 10 * 1000, fixedDelay = 7000*1000 )
    public void getWeixinAccessToken(){
        try {
            String token = null;
            //访问微信服务器的地址
            String requestUrl = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
            //创建一个json对象
            JSONObject json = httpsRequest(requestUrl,"GET",null);
            System.out.println("获取到的json格式的Token为:" + json);
            //判断json是否为空
            if (json!=null){
                try{
                    token = json.getString("access_token");
                    Long expires_in = json.getLong("expires_in");
//                    if (jedis.exists("token")) jedis.del("token");
                    //将获取的access_token放入redis对象中
//                    jedis.set("token", token, "NX", "EX", expires_in);
                }
                catch (Exception e){
                    e.printStackTrace();
                    System.out.println("系统异常！");
                }
            }else {
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}", json.get("errcode"), json.get("errmsg"));
            }
        } catch (Exception e) {
            logger.error("获取微信access_toke出错，信息如下");
            e.printStackTrace();
            this.getWeixinAccessToken();
        }
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s){}
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s){}
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            System.out.println("****** requestMethod： " + requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();
            // 当有数据需要提交时
            if (outputStr != null) {
                if (outputStr.trim().length() > 0) {
                    OutputStream outputStream = httpUrlConn.getOutputStream();
                    // 注意编码格式，防止中文乱码
                    outputStream.write(outputStr.getBytes("UTF-8"));
                    outputStream.close();
                }
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            System.out.println("****** buffer： " + buffer.toString());
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = JSON.parseObject(buffer.toString());
            System.out.println("****** jsonObject： " + jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
