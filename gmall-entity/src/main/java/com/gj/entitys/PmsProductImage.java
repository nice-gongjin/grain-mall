package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsProductImage implements Serializable {

//    @Column
    @TableId
    private String id;
//    @Column
    private String productId;
//    @Column
    private String imgName;
//    @Column
    private String imgUrl;

    public PmsProductImage() {
    }

}
