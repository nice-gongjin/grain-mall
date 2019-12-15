package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsBaseAttrValue implements Serializable {

    @TableId
//    @Column
    private String id;
//    @Column
    private String valueName;
//    @Column
    private String attrId;
//    @Column
    private String isEnabled;
//    @Transient
    private String urlParam;

}
