package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsSkuSaleAttrValue implements Serializable {

    @TableId
//    @Column("")
    String id;

//    @Column
    String skuId;

//    @Column
    String saleAttrId;

//    @Column
    String saleAttrValueId;

//    @Column
    String saleAttrName;

//    @Column
    String saleAttrValueName;

}
