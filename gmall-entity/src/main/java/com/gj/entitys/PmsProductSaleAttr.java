package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("pms_product_sale_attr")
public class PmsProductSaleAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id ;
    private String productId;
    private String saleAttrId;
    private String saleAttrName;

    @TableField(exist = false)
    private List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList;

}
