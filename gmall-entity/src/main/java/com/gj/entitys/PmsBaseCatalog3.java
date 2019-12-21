package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.Generated;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("pms_base_catalog3")
public class PmsBaseCatalog3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String catalog1Id;

    @TableField(exist = false)
    private List<PmsBaseCatalog3> catalog3List;

}
