package com.grain.mall.util.wechat;

/**
 * 通信使用到的一些常量
 */
public class WeChatConstant {
    /**
     * 请求消息类型
     */
    // 文本
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    // 图片
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    // 语音
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    // 视频
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    // 地理位置
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    // 链接
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    // 小视频
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO ="shortvideo";
    // 事件
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
    // 事件 - 关注
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    // 事件 - 取消关注
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    // 事件 - 扫描带参数的二维码
    public static final String EVENT_TYPE_SCAN = "scan";
    // 事件 - 上报地理位置
    public static final String EVENT_TYPE_LOCATION = "location";
    // 事件 - 点击底部菜单
    public static final String EVENT_TYPE_CLICK = "CLICK";
    // 事件 - 自定义菜单URl视图
    public static final String EVENT_TYPE_VIEW ="VIEW";
    // 事件 - 模板消息送达情况提醒
    public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH="TEMPLATESENDJOBFINISH";
    // 事件 - 事件的EventKey
    public static final String Event_Key="EventKey";
    //事件 - 事件的EventKey值
    public static final String CLICK_JOIN = "help_join";
    public static final String VIEW_HELP = "http://www.help.com/";
    public static final String VIEW_FILE = "http://www.files.com/";
    public static final String VIEW_ARTICLE = "http://www.articles.com/";
    /**
     * 响应消息类型
     */
    // 文本
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    // 文本
    public static final String RESP_MESSAGE_TYPE_IMAGE ="image";
    // 语音
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    // 音乐
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    // 图文
    public static final  String RESP_MESSAGE_TYPE_NEWS = "news";
    // 视频
    public static final String RESP_MESSAGE_TYPE_VIDEO ="video";
    /**
     * 基本信息
     */
    // 发送方微信账号
    public static final String FROM_USER_NAME = "FromUserName";
    // 接收方微信账号
    public static final String TO_USER_NAME = "ToUserName";
    // 消息id
    public static final String MSG_ID = "MsgId";
    // 消息类型
    public static final String MSG_TYPE = "MsgType";
    // 消息内容
    public static final String CONTENT = "Content";
    // 消息发送时间
    public static final String CREATE_TIME = "CreateTime";
    // 事件类型
    public static final String EVENT = "Event";

    /**
     *  下面是微信的接口请求地址
     */
    //获取微信callback IP地址 GET
    public static String CALLBACK_IP = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

    /**
     *  获取access_token的接口地址（GET） 限200（次/天）
     */
    public static String ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 获取JS_SDK_TICKET
     */
    public static String JS_SDK_TICKET_URL =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    /**
     * 获取网页授权的access_token的接口地址
     */
    public static String  OAUTH2_ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * 自定义菜单删除接口
     */
    public static String MENU_DELETE_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    /**
     * 自定义菜单的创建接口
     */
    public static String MENU_CREATE_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    /**
     * 自定义菜单的查询接口
     */
    public static String MENU_GET_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    //客服接口-添加客服账号
    public static String CUSTOM_ADD = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
    /**
     *  客服接口-发消息接口
     */
    public static String CUSTOM_SERVICE_URL =
            "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    /**
     * 发送模板消息接口
     */
    public static String SEND_TEMPLATE_URL =
            "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    /**
     * 创建标签接口
     * @Method :POST
     */
    public static String USE_TAG_CREATE_URL  =
            "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
    /**
     * 获取用户身上的标签
     * @Method:POST
     */
    public static String GET_INUSER_TAG_URL  =
            "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";
    /**
     * 批量为用户取消标签
     * @Method:POST
     */
    public static String UNTAGGING_USER_BATCH_URL  =
            "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
    /**
     * 创建个性化菜单
     * @Method :POST
     */
    public static String CREATE_PERSONALIZED_MENU_URL  =
            "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
    /**
     * 删除个性化菜单
     * @Method:
     */
    public static String DELETE_PERSONAL_MENU_URL  =
            "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN";
    /**
     * 给用户打标签的姐接口
     */
    public static String CREATE_USERTAG_URL =
            "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
    /**
     * 网页授权获取用户详细信息的的接口
     */
    public static String GET_USERINFO_URL =
            "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    /**
     * openid获取用户的基本信息的接口
     */
    public static String OPENID_USERINFO_URL =
            "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

}
