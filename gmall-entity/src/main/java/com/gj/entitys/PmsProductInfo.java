package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PmsProductInfo implements Serializable {

    @TableId
//    @Column
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

//    @Column
    private String productName;

//    @Column
    private String description;

//    @Column
    private  String catalog3Id;

//    @Transient
    private List<PmsProductSaleAttr> pmsProductSaleAttrList;
//    @Transient
    private List<PmsProductImage> pmsProductImageList;

}
