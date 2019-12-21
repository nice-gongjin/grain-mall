package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("pms_sku_sale_attr_value")
public class PmsSkuSaleAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String skuId;
    private String saleAttrId;
    private String saleAttrValueId;
    private String saleAttrName;
    private String saleAttrValueName;

}
