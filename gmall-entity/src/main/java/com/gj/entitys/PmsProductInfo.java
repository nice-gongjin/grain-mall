package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("pms_product_info")
public class PmsProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String productName;
    private String description;
    private  String catalog3Id;

    @TableField(exist = false)
    private List<PmsProductSaleAttr> pmsProductSaleAttrList;

    @TableField(exist = false)
    private List<PmsProductImage> pmsProductImageList;

}
