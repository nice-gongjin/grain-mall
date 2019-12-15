package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsProductSaleAttrValue implements Serializable {

    @TableId
//    @Column
    String id ;

//    @Column
    String productId;

//    @Column
    String saleAttrId;

//    @Column
    String saleAttrValueName;

//    @Transient
    String isChecked;

}
