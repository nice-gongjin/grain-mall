package com.gj.gmall.util.wechat;

import com.gj.entitys.Article;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gj.gmall.util.wechat.XmlUtil.mapToXML;

public class SendUtil {

    /**     封装微信服务器推送过来的requestMap的消息    */
    public static String handleRequest(Map<String,String> requestMap){
        Map<String,Object> resultMap = new HashMap<>(); //定义一个map来存储requestMap的CONTENT
        resultMap.put("ToUserName", requestMap.get(WeChatConstant.FROM_USER_NAME));
        resultMap.put("FromUserName", requestMap.get(WeChatConstant.TO_USER_NAME));
        resultMap.put("CreateTime", new Date().getTime());
        resultMap.put("MsgType", WeChatConstant.RESP_MESSAGE_TYPE_TEXT);
        Map<String,Object> map = new HashMap<>();   //定义一个map来存储其它字段的信息
        String type = requestMap.get(WeChatConstant.MSG_TYPE);  //获取微信服务器推送过来的消息的类型MsgType
        //根据MsgType的类型来处理事件
        if (WeChatConstant.REQ_MESSAGE_TYPE_TEXT.equals(type)){
            resultMap.put("Content", requestMap.get(WeChatConstant.CONTENT));
        }
        else if (WeChatConstant.REQ_MESSAGE_TYPE_IMAGE.equals(type)){
            //map.put("PicUrl", requestMap.get("PicUrl"));
            //map.put("MediaId", requestMap.get("MediaId"));  //MediaId: XX消息媒体id，可以调用获取临时素材接口拉取数据
            //map.put("MsgId", requestMap.get("MsgId"));  //MsgId: 消息id，64位整型
            Map<String,Object> item = new HashMap<>();
            item.put("MediaId", requestMap.get("MediaId"));
            resultMap.put("Image", item);
        }
        else if (WeChatConstant.REQ_MESSAGE_TYPE_VOICE.equals(type)){
            //map.put("Format", requestMap.get("Format"));  //语音格式：amr
            //map.put("MsgID", requestMap.get("MsgID"));
            //Recognition: 语音识别结果，UTF8编码
            //if (requestMap.containsKey("Recognition")) map.put("Recognition", requestMap.get("Recognition"));
            Map<String,Object> item = new HashMap<>();
            item.put("MediaId", requestMap.get("MediaId"));
            resultMap.put("Voice", item);
        }
        else if (WeChatConstant.REQ_MESSAGE_TYPE_VIDEO.equals(type) || WeChatConstant.REQ_MESSAGE_TYPE_SHORTVIDEO.equals(type)) {
            //map.put("ThumbMediaId", requestMap.get("ThumbMediaId"));
            //map.put("MsgId", requestMap.get("MsgId"));
            Map<String,Object> item = new HashMap<>();
            item.put("MediaId", requestMap.get("MediaId"));
            item.put("Title", requestMap.get("视频消息"));
            item.put("Description", requestMap.get("视频"));
            resultMap.put("Video", item);
        }
        else if (WeChatConstant.EVENT_TYPE_LOCATION.equals(type)){
            //map.put("MsgId", requestMap.get("MsgId"));
            String content = "地理位置信息如下：\n经度：" + requestMap.get("Location_Y") +
                    "，\n维度：" + requestMap.get("Location_X") +
                    "，\n地图缩放大小：" + requestMap.get("Scale") +
                    "，\n地理位置：" + requestMap.get("Label");
            resultMap.put("Content", content);
        }
        else if (WeChatConstant.REQ_MESSAGE_TYPE_LINK.equals(type)){
            //map.put("MsgId", requestMap.get("MsgId"));
            Map<String,Object> item = new HashMap<>();
            Map<String,Object> itemContent = new HashMap<>();
            itemContent.put("Title", requestMap.get("Title"));
            itemContent.put("Description", requestMap.get("Description"));
            itemContent.put("PicUrl", requestMap.get("Url"));
            itemContent.put("Url", "http://www.baidu.com");
            item.put("item",itemContent);
            resultMap.put("Articles", item);
            resultMap.put("ArticleCount", 1);
        }
        else if (WeChatConstant.RESP_MESSAGE_TYPE_MUSIC.equals(type)){
            Map<String,Object> item = new HashMap<>();
            item.put("Title", "音乐消息");
            item.put("Description", "音乐");
            if (requestMap.containsKey("MusicUrl")) item.put("MusicUrl", requestMap.get("MusicUrl"));
            if (requestMap.containsKey("HQMusicUrl")) item.put("HQMusicUrl", requestMap.get("HQMusicUrl"));
            if (requestMap.containsKey("ThumbMediaId")) item.put("ThumbMediaId", requestMap.get("ThumbMediaId"));
            resultMap.put("Music", item);
        }
        //如果是菜单事件类型
        else if (WeChatConstant.REQ_MESSAGE_TYPE_EVENT.equals(type)){
            // 事件类型
            String eventType = requestMap.get(WeChatConstant.EVENT);
            if (WeChatConstant.EVENT_TYPE_SUBSCRIBE.equals(eventType)){
                //用户未关注时：事件KEY值，qrscene_为前缀，后面为二维码的参数值
                //用户已关注时：事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
                if (requestMap.containsKey("EventKey")) map.put("EventKey", requestMap.get("EventKey"));
                //二维码的ticket，可用来换取二维码图片
                if (requestMap.containsKey("Ticket")) map.put("Ticket", requestMap.get("Ticket"));
                map = null;
                resultMap.put("Content", "谢谢您的关注！");
            }
            else if (WeChatConstant.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)){
                Iterator<String> iter = resultMap.keySet().iterator();
                while(iter.hasNext()){
                    if(iter.next().equals("ToUserName")) iter.remove();
                }
                resultMap.put("ToUserName", "oyUEjuFrDsmvu8SFnq_wFm8ow8z8");
                resultMap.put("Content", WeChatConstant.FROM_USER_NAME + "用户取消了对你的关注！");
            }
            else if (WeChatConstant.EVENT_TYPE_SCAN.equals(eventType)){ //SCAN表示用户已经关注了该微信公众号
                resultMap.put("Content", "暂时不能处理带参数二维码事件，敬请见谅！");
            }
            else if ("LOCATION".equals(eventType)){
                String content = "地理位置信息如下：\n经度：" + requestMap.get("Longitude") +
                        "，\n纬度：" + requestMap.get("Latitude") + "，\n地理位置精度：" + requestMap.get("Precision");
                resultMap.put("Content", "您发送的是上报地理位置事件！\n" + content);
            }
            else if (WeChatConstant.EVENT_TYPE_CLICK.equals(eventType)){
                //EventKey: 事件KEY值，与自定义菜单接口中KEY值对应
                String tests = null;
                String eventKey = requestMap.get(WeChatConstant.Event_Key);
                if (WeChatConstant.CLICK_JOIN.equals(eventKey)) tests = "加我好友事件，微信公众号搜索：'gongjin' 即可";
                tests = tests==null?"您发送的是自定义菜单点击事件！\n菜单的EventKey为：" + requestMap.get("EventKey"):tests;
                resultMap.put("Content", tests);
            }
            else if ("VIEW".equals(eventType)){
                //EventKey: 事件KEY值，设置的跳转URL
                String tests = null;
                String eventKey = requestMap.get(WeChatConstant.Event_Key);
                if (WeChatConstant.VIEW_HELP.equals(eventKey)) tests = "帮助";
                else if (WeChatConstant.VIEW_FILE.equals(eventKey)) tests = "SpringBoot资料";
                else if (WeChatConstant.VIEW_ARTICLE.equals(eventKey)) tests = "获取海量好文";
                resultMap.put("Content", "您发送的是自定义菜单网页事件！\n菜单是：" + tests);
            }
        }
        map = null;

        return mapToXML(resultMap);
    }

    /**
     * 回复文本消息
     */
    public static String sendTextMsg(Map<String,String> requestMap, String content){
        Map<String,Object> map = new HashMap<>();
        map.put("ToUserName", requestMap.get(WeChatConstant.FROM_USER_NAME));
        map.put("FromUserName", requestMap.get(WeChatConstant.TO_USER_NAME));
        map.put("MsgType", WeChatConstant.RESP_MESSAGE_TYPE_TEXT);
        map.put("CreateTime", new Date().getTime());
        map.put("Content", content);

        return mapToXML(map);
    }

    /**
     * 回复图文消息
     */
    public static String sendArticleMsg(Map<String,String> requestMap, List<Article> articles){
        if(articles == null || articles.size()<1){
            return "";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ToUserName", requestMap.get(WeChatConstant.FROM_USER_NAME));
        map.put("FromUserName", requestMap.get(WeChatConstant.TO_USER_NAME));
        map.put("MsgType", WeChatConstant.RESP_MESSAGE_TYPE_NEWS);
        map.put("CreateTime", new Date().getTime());
        List<Map<String, Object>> articleMaps = new ArrayList<>();
        for(Article article : articles){
            Map<String,Object> item = new HashMap<>();
            Map<String,Object> itemContent = new HashMap<>();
            itemContent.put("Title", article.getTitle());
            itemContent.put("Description", article.getDescription());
            itemContent.put("PicUrl", article.getPicUrl());
            itemContent.put("Url", article.getUrl());
            item.put("item",itemContent);
            articleMaps.add(item);
        }
        map.put("Articles", articleMaps);
        map.put("ArticleCount", articleMaps.size());

        return mapToXML(map);
    }

    /**
     * 判断是否是QQ表情
     * @param content
     * @return result
     */
    public static boolean isQqFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) result = true;

        return result;
    }

    /**
     *  自定义的回复文本
     * @param requestMap
     */
    public static String customerText(Map<String,String> requestMap){
        StringBuffer buffer = new StringBuffer();
        buffer.append("您好，我是小8，请回复数字选择服务：").append("\n\n");
        buffer.append("11 可查看测试单图文").append("\n");
        buffer.append("12  可测试多图文发送").append("\n");
        buffer.append("13  可测试网址").append("\n");
        buffer.append("或者您可以尝试发送表情").append("\n\n");
        buffer.append("回复“1”显示此帮助菜单").append("\n");

        Map<String,Object> map = new HashMap<>();
        map.put("FromUserName", requestMap.get(WeChatConstant.TO_USER_NAME));
        map.put("ToUserName", requestMap.get(WeChatConstant.FROM_USER_NAME));
        map.put("MsgType", WeChatConstant.RESP_MESSAGE_TYPE_TEXT);
        map.put("CreateTime", new Date().getTime());
        String Content = String.valueOf(buffer);
        map.put("Content", Content);

        return mapToXML(map);
    }

}
