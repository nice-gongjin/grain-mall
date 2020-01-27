package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("wms_ware_order_task")
public class WmsWareOrderTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String wareId;
    private String orderId;
    private String consignee;
    private String consigneeTel;
    private String deliveryAddress;
    private String orderComment;
    private String paymentWay;
    private String taskStatus;
    private String orderBody;
    private String trackingNo;
    private String taskComment;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
