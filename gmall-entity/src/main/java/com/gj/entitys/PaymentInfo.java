package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentInfo implements Serializable {

//    @Column
    @TableId
    private String  id;

//    @Column
    private String outTradeNo;

//    @Column
    private String orderId;

//    @Column
    private String alipayTradeNo;

//    @Column
    private BigDecimal totalAmount;

//    @Column
    private String Subject;

//    @Column
    private String paymentStatus;

//    @Column
    private Date createTime;

//    @Column
    private Date callbackTime;

//    @Column
    private String callbackContent;

}
