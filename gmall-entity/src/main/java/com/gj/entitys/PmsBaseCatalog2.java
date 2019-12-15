package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsBaseCatalog2 implements Serializable {

    @TableId
//    @Column
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
//    @Column
    private String name;
//    @Column
    private String catalog1Id;

//    @Transient
//    private List<BaseCatalog3> catalog3List;

}
