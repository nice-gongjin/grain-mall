package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PmsProductSaleAttr implements Serializable {

    @TableId
//    @Column
    String id ;

//    @Column
    String productId;

//    @Column
    String saleAttrId;

//    @Column
    String saleAttrName;


//    @Transient
    List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList;

}
