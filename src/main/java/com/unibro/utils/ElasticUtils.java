/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.utils;

import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 *
 * @author THOND
 */
public class ElasticUtils {

    public static SearchSourceBuilder getSourceBuilder(List<RequestFilter> filters) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (filters == null || filters.isEmpty()) {
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            return sourceBuilder;
        }
        BoolQueryBuilder q = QueryBuilders.boolQuery();
        for (RequestFilter filter : filters) {
            if (filter.getType().equals(RequestFilter.EQUAL)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.matchQuery(filter.getName(), filter.getValue()).operator(Operator.AND));
                } else {
                    q.should(QueryBuilders.matchQuery(filter.getName(), filter.getValue()).operator(Operator.OR));
                }
            }
            if (filter.getType().equals(RequestFilter.REGEX)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.regexpQuery(filter.getName(), filter.getValue().toString()));
                } else {
                    q.should(QueryBuilders.regexpQuery(filter.getName(), filter.getValue().toString()));
//                    q.should(QueryBuilders.matchQuery(filter.getName(), filter.getValue()));
                }
            }
            if (filter.getType().equals(RequestFilter.CONTAIN)) {
                if (filter.getRequired()) {
//                    q.must(QueryBuilders.regexpQuery(filter.getName(), ".*" + filter.getValue() + ".*"));
                    QueryStringQueryBuilder q_builder = QueryBuilders.queryStringQuery(filter.getValue().toString()).defaultField(filter.getName());
                    q_builder.type(null);
                    q.must(q_builder);

                } else {
//                    q.should(QueryBuilders.matchQuery(filter.getName(), filter.getValue()));
                    QueryStringQueryBuilder q_builder = QueryBuilders.queryStringQuery(filter.getValue().toString()).defaultField(filter.getName());
                    q_builder.type(null);
                    q.should(q_builder);
                }
            }
            if (filter.getType().equals(RequestFilter.GREATER)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.rangeQuery(filter.getName()).from(filter.getValue()));
                } else {
                    q.should(QueryBuilders.rangeQuery(filter.getName()).from(filter.getValue()));
                }

            }
            if (filter.getType().equals(RequestFilter.LESS)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.rangeQuery(filter.getName()).to(filter.getValue()));
                } else {
                    q.should(QueryBuilders.rangeQuery(filter.getName()).to(filter.getValue()));
                }
            }
            if (filter.getType().equals(RequestFilter.IN)) {
                List list = (List) filter.getValue();
                if (filter.getRequired()) {
                    q.must(QueryBuilders.termsQuery(filter.getName(), list));
                } else {
                    q.should(QueryBuilders.termsQuery(filter.getName(), list));
                }
            }
        }
        sourceBuilder.query(q);
        return sourceBuilder;
    }

    public static SearchSourceBuilder getSourceBuilder(int from, int size, String sortName, int sortType, List<RequestFilter> filters) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (filters == null || filters.isEmpty()) {
            MatchAllQueryBuilder q = QueryBuilders.matchAllQuery();
            sourceBuilder.query(q);
            if (size > 0) {
                sourceBuilder.query(q).from(from);
                sourceBuilder.query(q).size(size);
            }
            if (sortType == 1) {
                sourceBuilder.query(q).sort(sortName, SortOrder.ASC);
            }
            if (sortType == -1) {
                sourceBuilder.query(q).sort(sortName, SortOrder.DESC);
            }
            return sourceBuilder;
        }
        BoolQueryBuilder q = QueryBuilders.boolQuery();
        for (RequestFilter filter : filters) {
            if (filter.getType().equals(RequestFilter.EQUAL)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.matchQuery(filter.getName(), filter.getValue()).operator(Operator.AND));
                } else {
                    q.should(QueryBuilders.matchQuery(filter.getName(), filter.getValue()).operator(Operator.OR));
                }
            }
            if (filter.getType().equals(RequestFilter.REGEX)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.regexpQuery(filter.getName(), filter.getValue().toString()));
                } else {
                    q.should(QueryBuilders.regexpQuery(filter.getName(), filter.getValue().toString()));
//                    q.should(QueryBuilders.matchQuery(filter.getName(), filter.getValue()));
                }
            }
            if (filter.getType().equals(RequestFilter.CONTAIN)) {
                if (filter.getRequired()) {
//                    q.must(QueryBuilders.regexpQuery(filter.getName(), ".*" + filter.getValue() + ".*"));
                    QueryStringQueryBuilder q_builder = QueryBuilders.queryStringQuery(filter.getValue().toString()).defaultField(filter.getName());
                    q_builder.type(null);
                    q.must(q_builder);
                } else {
                    QueryStringQueryBuilder q_builder = QueryBuilders.queryStringQuery(filter.getValue().toString()).defaultField(filter.getName());
                    q_builder.type(null);
                    q.should(q_builder);
//                    q.should(QueryBuilders.matchQuery(filter.getName(), filter.getValue()));
                }
            }
            if (filter.getType().equals(RequestFilter.GREATER)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.rangeQuery(filter.getName()).from(filter.getValue()));
                } else {
                    q.should(QueryBuilders.rangeQuery(filter.getName()).from(filter.getValue()));
                }

            }
            if (filter.getType().equals(RequestFilter.LESS)) {
                if (filter.getRequired()) {
                    q.must(QueryBuilders.rangeQuery(filter.getName()).to(filter.getValue()));
                } else {
                    q.should(QueryBuilders.rangeQuery(filter.getName()).to(filter.getValue()));
                }
            }
            if (filter.getType().equals(RequestFilter.IN)) {
                List list = (List) filter.getValue();
                if (filter.getRequired()) {
                    q.must(QueryBuilders.termsQuery(filter.getName(), list));
                } else {
                    q.should(QueryBuilders.termsQuery(filter.getName(), list));
                }
            }
        }
        if (size > 0) {
            sourceBuilder.query(q).from(from);
            sourceBuilder.query(q).size(size);
        }
        if (sortType == 1) {
            sourceBuilder.query(q).sort(sortName, SortOrder.ASC);
        }
        if (sortType == -1) {
            sourceBuilder.query(q).sort(sortName, SortOrder.DESC);
        }
        return sourceBuilder;
    }
}
