package com.gj.entitys;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String keyword;

    private String catalog3Id;

    private List<PmsSkuAttrValue> pmsSkuAttrValueList;

    private List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList;

}
