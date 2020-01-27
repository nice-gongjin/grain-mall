package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("wms_ware_sku")
public class WmsWareSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String skuId;
    private String warehouseId;
    private Integer stock;
    private String stockName;
    private Integer stockLocked;

}
