package com.gj.entitys;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private String picUrl;
    private String url;

}
