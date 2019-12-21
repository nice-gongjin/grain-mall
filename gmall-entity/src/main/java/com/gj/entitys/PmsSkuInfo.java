package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@TableName("pms_sku_info")
public class PmsSkuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String productId;
    private BigDecimal price;
    private String skuName;
    private BigDecimal weight;
    private String skuDesc;
    private String catalog3Id;
    private String skuDefaultImg;

    @TableField(exist = false)
    private List<PmsSkuImage> pmsSkuImageList;

    @TableField(exist = false)
    private List<PmsSkuAttrValue> pmsSkuAttrValueList;

    @TableField(exist = false)
    private List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList;

}
