package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gj.entitys.OmsCartItem;
import com.gj.entitys.OmsOrder;
import com.gj.entitys.OmsOrderItem;
import com.gj.gmall.annotation.LoginRequied;
import com.gj.gmall.myException.MyException;
import com.gj.services.CartService;
import com.gj.services.OrderService;
import com.gj.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    CartService cartService;
    @Reference
    UserService userService;
    @Reference
    OrderService orderService;

    /**
     * 购物车列表界面对购物车的数量的异步修改
     */
    @LoginRequied(loginSuccess = true)
    @RequestMapping(value = "/trade",method = RequestMethod.POST)
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        // 判断用户是否登录
        String userId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");

        // 查询订单的收货地址
        // List<UmsMemberReceiveAddress> userAddressList = userService.getOrderAdderss(userId);

        // 将购物车的集合转化为订单页面的计算清单集合
        List<OmsCartItem> cartLists = cartService.getCartList(userId);
        // 定义返回的orderDetailList结果集
        List<OmsOrderItem> orderDetailList = new ArrayList<>();
        // 计算被勾选的商品的总额
        BigDecimal totalAmount = new BigDecimal("0");
        if (null != cartLists && cartLists.size() > 0) {
            for (OmsCartItem cartList : cartLists) {
                if ("1".equals(cartList.getIsChecked())) {
                    OmsOrderItem omsOrderItem = new OmsOrderItem();
                    omsOrderItem.setProductSkuId(cartList.getProductSkuId());
                    omsOrderItem.setProductName(cartList.getProductName());
                    omsOrderItem.setProductQuantity(cartList.getQuantity());
                    omsOrderItem.setProductPrice(cartList.getPrice());
                    omsOrderItem.setProductPic(cartList.getProductPic());
                    BigDecimal totalPrice = cartList.getTotalPrice();
                    totalAmount = totalAmount.add(totalPrice);
                }
            }
        }

        // 生成随机的订单码，以便检验订单是否重复提交
        String tradeCode = UUID.randomUUID().toString();
        Boolean addTradeCode = cartService.addTradeCode(userId, tradeCode);

        // 返回orderDetailList
        // modelMap.put("userAddressList", userAddressList);
        modelMap.put("orderDetailList", orderDetailList);
        modelMap.put("totalAmount", totalAmount);
        if (!addTradeCode) tradeCode = "";
        modelMap.put("tradeCode", tradeCode);
        try {
            response.sendRedirect("http://order.gmall.com:10015/trade.html");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("****** responseError = " + e.getMessage());
        }

        return "toTrade";
    }

    /**
     * 提交订单
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitOrder(@RequestBody String requests, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String result = "fail";
        // 获取用户Id
        String userId = (String) request.getAttribute("memberId");
        // 获取表单的参数
        // consignee=收件人：龚进;deliveryAddress=具体地址：江西省赣州市章贡区XXXX大道XX科技;paymentWay=ONLINE;orderComment=666阿飞;tradeCode=
        try {
            String reqParams = URLDecoder.decode(requests, "utf-8");
            // 将参数requests转为Map类型
            Map<String, Object> requestMap = new HashMap<>();
            String[] split = reqParams.split("&");
            if (split.length > 0) {
                for (String s : split) {
                    if (StringUtils.isNotBlank(s)) {
                        String[] strings = s.split("=");
                        // 只要key不为null即可
                        if (null != strings[0]) {
                            // 存在只有key而没有value的情况
                            if (strings.length > 1) requestMap.put(strings[0], strings[1]);
                            else requestMap.put(strings[0], "");
                        }
                    }
                }
            }
            System.out.println("****** reqParams = " + requestMap);
            // 先检验订单码tradeCode是否存在
            Boolean isExist = userService.cheackTradeCode(userId, (String) requestMap.get("tradeCode"));
            if (!isExist) {
                throw new MyException("不能重复提交！");
            }
            // 1.根据用户Id获得要购买的商品列表信息
            List<OmsCartItem> cartLists = cartService.getCartList(userId);
            // 2.循环购物车中的数据，将购物车的对象封装为订单对象
            OmsOrder omsOrder = new OmsOrder();
            omsOrder.setAutoConfirmDay(7);
            omsOrder.setConfirmStatus(0);
            omsOrder.setCreateTime(new Date());
            omsOrder.setDiscountAmount(null);
            omsOrder.setMemberId(userId);
            omsOrder.setMemberUsername((String) request.getAttribute("nickName"));
            String orderNote = (String) requestMap.get("orderComment");
            if (StringUtils.isNotBlank(orderNote)) omsOrder.setNote(orderNote);
            else omsOrder.setNote("订单备注");
            // 封装订单编号
            String orderSn = "gmall";
            orderSn = orderSn + System.currentTimeMillis();
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
            orderSn = orderSn + simpleFormatter.format(new Date());
            omsOrder.setOrderSn(orderSn); // 外部订单号
            omsOrder.setOrderType(1);
            omsOrder.setSourceType(0);
            omsOrder.setStatus(0);
            omsOrder.setReceiverName((String) requestMap.get("consignee"));
            String address = (String) requestMap.get("deliveryAddress");
            omsOrder.setReceiverDetailAddress(address);
            if (StringUtils.isNotBlank(address) && address.contains("省")) {
                String[] provice = address.split("省");
                omsOrder.setReceiverCity(provice[0] + "省");
                if (provice.length > 1 && StringUtils.isNotBlank(provice[1]) && address.contains("市")) {
                    String[] city = provice[1].split("市");
                    omsOrder.setReceiverCity(city[0] + "市");
                    omsOrder.setReceiverRegion(city[1]);
                }
            }
            omsOrder.setReceiverPhone("18888888888");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 2);
            omsOrder.setReceiveTime(calendar.getTime());
            // 定义订单的总金额
            BigDecimal orderTotalPrice = new BigDecimal("0");
            // 封装商品的列表数据
            List<OmsOrderItem> omsOrderItems = new ArrayList<>();
            // 每循环一个商品必须要检验当前商品的价格和库存是否符合购买要求
            for (OmsCartItem cartList : cartLists) {
                if ("1".equals(cartList.getIsChecked())) {
                    // 检验价格，正式环境应该写在SkuinfoService中，这样可以避免在OrderServiceImpl中调用SkuinfoMapper
                    Boolean cheackPrice = orderService.cheackPrice(cartList);
                    if (!cheackPrice) return result;
                    // 检验库存
                    Integer stock = orderService.cheackStock(cartList.getProductSkuId());
                    if (stock <= 0) return result;
                    // 3.将购物车的对象封装为订单对象
                    OmsOrderItem orderItem = new OmsOrderItem();
                    orderItem.setProductSkuId(cartList.getProductSkuId());
                    orderItem.setProductName(cartList.getProductName());
                    orderItem.setProductQuantity(cartList.getQuantity());
                    orderItem.setProductPrice(cartList.getPrice());
                    orderItem.setOrderSn(orderSn); // 外部订单号，防止订单重复
                    orderItem.setProductPic(cartList.getProductPic());
                    orderItem.setRealAmount(cartList.getTotalPrice());
                    orderItem.setProductSkuCode("666666");
                    orderItem.setProductId(cartList.getProductId());
                    orderItem.setProductSn("商品skuId为" + UUID.randomUUID()); // 在仓库中的skuId
                    orderItem.setProductCategoryId(cartList.getProductCategoryId());
                    orderTotalPrice = orderTotalPrice.add(cartList.getPrice());
                    omsOrderItems.add(orderItem);
                    // 4.将订单信息写入数据库
                    Boolean saveOrders = orderService.saveOrders(omsOrder);
                    if (!saveOrders) return result;
                    // 5.删除购物车对应的商品数据
                    boolean delete = cartService.deleteById(cartList.getProductId());
                    if (!delete) return result;
                    // 6.重定向到支付系统，等待用户完成付款的步骤
                    try {
                        response.sendRedirect("http://payment.gmall.com:18888/index.html");
                    } catch (IOException e) {
                        System.out.println("****** responseError = " + e.getMessage());
                        throw new MyException("重定向到支付界面失败！");
                    }
                }
            }
            omsOrder.setPayAmount(orderTotalPrice);
            omsOrder.setTotalAmount(orderTotalPrice);
            omsOrder.setOmsOrderItems(omsOrderItems);
            result = "success";
        } catch (UnsupportedEncodingException e) {
            throw new MyException("参数转换错误！");
        }

        return result;
    }

}
