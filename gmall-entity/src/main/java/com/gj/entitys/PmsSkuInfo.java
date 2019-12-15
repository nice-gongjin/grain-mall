package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PmsSkuInfo implements Serializable {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId
    String id;

//    @Column
    String productId;

//    @Column
    BigDecimal price;

//    @Column
    String skuName;

//    @Column
    BigDecimal weight;

//    @Column
    String skuDesc;

//    @Column
    String catalog3Id;

//    @Column
    String skuDefaultImg;

//    @Transient
    List<PmsSkuImage> pmsSkuImageList;

//    @Transient
    List<PmsSkuAttrValue> pmsSkuAttrValueList;

//    @Transient
    List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList;

}
