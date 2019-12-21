package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("oms_company_address")
public class OmsCompanyAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    private String  addressName;
    private Integer sendStatus;
    private Integer receiveStatus;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detailAddress;

}
