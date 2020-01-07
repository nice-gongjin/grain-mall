package com.gj.entitys;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ums_member")
public class UmsMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String memberLevelId;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String icon;
    private Integer gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthday;
    private String city;
    private String job;
    private String personalizedSignature;
    private Integer sourceUid;
    private Integer sourceType;
    private Integer integration;
    private Integer growth;
    private Integer luckeyCount;
    private Integer historyIntegration;
    private String accessCode;
    private String accessToken;

}
