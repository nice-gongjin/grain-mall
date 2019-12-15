package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class OmsCompanyAddress implements Serializable {

    @TableId
    private String id;
    private String  addressName;
    private int sendStatus;
    private int receiveStatus;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detailAddress;

}
