package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsBaseCatalog1 implements Serializable {

    @TableId
//    @Column
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
//    @Column
    private String name;

//    @Transient
//    private List<BaseCatalog2> catalog2s;

}
