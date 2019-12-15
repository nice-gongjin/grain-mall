package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OmsCartItem implements Serializable {

    @TableId
    private String id;
    private String productId;
    private String productSkuId;
    private String memberId;
    private int quantity;
    private BigDecimal price;
    private String sp1;
    private String sp2;
    private String sp3;
    private String productPic;
    private String productName;
    private String productSubTitle;
    private String productSkuCode;
    private String memberNickname;
    private Date createDate;
    private Date modifyDate;
    private int deleteStatus;
    private String productCategoryId;
    private String productBrand;
    private String productSn;
    private String productAttr;

}
