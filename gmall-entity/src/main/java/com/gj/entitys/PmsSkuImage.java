package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsSkuImage implements Serializable {

    @TableId
//    @Column
    String id;
//    @Column
    String skuId;
//    @Column
    String imgName;
//    @Column
    String imgUrl;
//    @Column
    String productImgId;
//    @Column
    String isDefault;

}
