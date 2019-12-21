package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("pms_product_sale_attr_value")
public class PmsProductSaleAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id ;
    private String productId;
    private String saleAttrId;
    private String saleAttrValueName;

    @TableField(exist = false)
    private String isChecked;

}
