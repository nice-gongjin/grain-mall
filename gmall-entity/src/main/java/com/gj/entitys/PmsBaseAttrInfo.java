package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("pms_base_attr_info")
public class PmsBaseAttrInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String attrName;
    private String catalog3Id;
    private String isEnabled;

    @TableField(exist = false)
    private List<PmsBaseAttrValue> attrValueList;

}
