package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("pms_base_catalog2")
public class PmsBaseCatalog2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String catalog1Id;

    @TableField(exist = false)
    private List<PmsBaseCatalog3> catalog3List;

}
