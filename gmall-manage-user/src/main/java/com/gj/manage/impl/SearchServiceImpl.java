package com.gj.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsSkuAttrValue;
import com.gj.entitys.PmsSkuInfo;
import com.gj.entitys.SearchParams;
import com.gj.manage.mapper.SearchMapper;
import com.gj.services.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchServiceImpl extends ServiceImpl<SearchMapper, PmsSkuInfo> implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<PmsSkuInfo> searchLists(SearchParams searchParams) {
        String searchDsl = getSearchDsl(searchParams);
        System.out.println("****** searchDsl = " + searchDsl);
        List<PmsSkuInfo> pmsSkuInfos = new ArrayList<>();
        Search search = new Search.Builder(searchDsl).addIndex("gjgmall").addType("PmsSkuInfo").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        }catch (Exception e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<PmsSkuInfo, Void>> hits = null;
        if (null != execute) {
            hits = execute.getHits(PmsSkuInfo.class);
        }
        if (null != hits) {
            for (SearchResult.Hit<PmsSkuInfo, Void> hit : hits) {
                PmsSkuInfo source = hit.source;
                pmsSkuInfos.add(source);
            }
        }
        Set<String> hashSet = new HashSet<>();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo.getPmsSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                hashSet.add(valueId);
            }
        }

        return pmsSkuInfos;
    }

    private String getSearchDsl(SearchParams searchParams) {
        String keyword = searchParams.getKeyword();
        String catalog3Id = searchParams.getCatalog3Id();
        List<PmsSkuAttrValue> pmsSkuAttrValueLists = searchParams.getPmsSkuAttrValueList();
        // jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // filter
        if (StringUtils.isNotBlank(keyword)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);

        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (null != pmsSkuAttrValueLists) {
            for (PmsSkuAttrValue pmsSkuAttrValueList : pmsSkuAttrValueLists) {
                String valueId = pmsSkuAttrValueList.getValueId();
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", valueId);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        // query
        searchSourceBuilder.query(boolQueryBuilder);
        // from
        searchSourceBuilder.from(0);
        // size
         searchSourceBuilder.size(20);
        // highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color: red'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlight(highlightBuilder);
        // aggs
        TermsBuilder groupby_attr = AggregationBuilders.terms("groupby_attr").field("skuAttrValueList.valueId");
        searchSourceBuilder.aggregation(groupby_attr);
        // sort
        searchSourceBuilder.sort("id", SortOrder.DESC);

        String query = searchSourceBuilder.toString();
        System.out.println("****** query = " + query);

        return query;
    }

}
