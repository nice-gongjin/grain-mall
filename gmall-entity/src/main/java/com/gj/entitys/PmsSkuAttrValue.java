package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsSkuAttrValue implements Serializable {

    @TableId
//    @Column
    String id;

//    @Column
    String attrId;

//    @Column
    String valueId;

//    @Column
    String skuId;

}
