package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsBaseAttrInfo implements Serializable {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId
//    @Column
    private String id;
//    @Column
    private String attrName;
//    @Column
    private String catalog3Id;
//    @Column
    private String isEnabled;
//    @Transient
//    List<BaseAttrValue> attrValueList;

}
