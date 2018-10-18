package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.config.EsConfig;
import com.enums.DataSourceEnum;

@Component
public class ElasticsearchUtils {
	
	private static Logger log = Logger.getLogger(ElasticsearchUtils.class);
	
	@Autowired
    private EsConfig esConfig;
	
	@Autowired
    private Client client;
	
	
	public IndexResponse addIndex (Object o, DataSourceEnum data) {
		IndexResponse response = null;
		String jsonStr = JSONObject.toJSONString(o);
		//Client client = esConfig.getClient();
		if (client == null) {
			return response;
		}
		System.out.println(client);
		try {
			 response = client.prepareIndex(data.getIndex(), data.getIndex()).setSource(jsonStr,XContentType.JSON).get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//client.close();
		}
		if (response == null) {
			return response;
		}
		log.info("json索引名称:" + response.getIndex() + ",json类型:" + response.getType()
		                      + ",json文档ID:" + response.getId()  + ",当前实例json状态:" + response.status());
		return response;
	}
	
	public Object findAll (DataSourceEnum database) {
		Client client = esConfig.getClient();
		List<Map<String, Object>> retList = null;
		if (client == null) {
			return null;
		}
		try {
			retList = new ArrayList<Map<String, Object>>();
			QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
	        SearchRequestBuilder requetBuilder = client.prepareSearch(database.getIndex()).setTypes(database.getTable()).setQuery(queryBuilder);
	        //requetBuilder.addSort(SortBuilders.fieldSort("timestamps").unmappedType("date").order(SortOrder.DESC)); //如果是嵌套
	        requetBuilder.addSort(SortBuilders.fieldSort("timestamps").order(SortOrder.DESC));
	        SearchResponse response = requetBuilder.execute().actionGet();
	        if (response != null && response.getHits() != null) {
	        	log.info("totalCount=" + response.getHits().getTotalHits());
	        	 for (SearchHit searchHit:response.getHits()) {
	        		 System.out.println(JSONObject.toJSONString(searchHit.getSourceAsMap()));
	        		 retList.add(searchHit.getSourceAsMap());
	 	        }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		return retList;
	}
	
	public Object findByPage (DataSourceEnum database, int startPage, int pageSize) {
		Client client = esConfig.getClient();
		List<Map<String, Object>> retList = null;
		if (client == null) {
			return null;
		}
		try {
			retList = new ArrayList<Map<String, Object>>();
			QueryBuilder queryBuilder = QueryBuilders.rangeQuery("timestamps").
					gte("2018-11-04T00:00:00").
					lt("2018-11-06T23:59:59").
					includeLower(true).  // 包含上界
		            includeUpper(true);  // 包含下界
			//QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
			SearchRequestBuilder requetBuilder = client.prepareSearch(database.getIndex()).setTypes(database.getTable()).setQuery(queryBuilder);
			requetBuilder.setFrom(startPage); //从第几条开始
			requetBuilder.setSize(pageSize);  //查询多少条
			requetBuilder.addSort(SortBuilders.fieldSort("timestamps").order(SortOrder.DESC));
	        SearchResponse response = requetBuilder.execute().actionGet();
	        if (response != null && response.getHits() != null) {
	        	 log.info("startPage:" + startPage + ",pageSize=" + pageSize + ",totalCount=" + response.getHits().getTotalHits());
	        	 for (SearchHit searchHit:response.getHits()) {
	        		 retList.add(searchHit.getSourceAsMap());
	 	        }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		//termsQuerys();
		return retList;
	}
	
	public Object findByPageNew (DataSourceEnum database, int startPage, int pageSize) {
		Client client = esConfig.getClient();
		List<Map<String, Object>> retList = null;
		if (client == null) {
			return null;
		}
		try {
			//QueryBuilder queryBuilder_1 = QueryBuilders.multiMatchQuery("music", "name", "interest");//搜索name中或interest中包含有music的文档（必须与music一致）
			//.must(QueryBuilders.rangeQuery("timestamps").gte("2018-11-01T00:00:00").lt("2018-11-06T23:59:59").includeLower(true).includeUpper(true))
		    //.must(QueryBuilders.multiMatchQuery("userId", "*0*"));
			//TermsAggregationBuilder builder = AggregationBuilders.terms("count_imsi").field("imsi");
			//SearchResponse response = client.prepareSearch(database.getIndex()).setTypes(database.getTable()).setQuery(query).addAggregation(builder)
			
			retList = new ArrayList<Map<String, Object>>();
			BoolQueryBuilder query = QueryBuilders.boolQuery();
			
			/*
			TermsLookupQueryBuilder terms = QueryBuilders
                    .termsLookupQuery("uuid")
                    .lookupIndex("user")
                    .lookupType("user")
                    .lookupId("5")
                    .lookupPath("uuids");
			
			HasChildQueryBuilder   hQuery = QueryBuilders
                    .hasChildQuery("instance", QueryBuilders
                    .hasChildQuery("instance_permission", terms));*/
   
			
			//query.must(QueryBuilders.wildcardQuery("instanceName", "duan*"));
			//query.must(QueryBuilders.wildcardQuery("ip", "*7*"));
			//query.must(QueryBuilders.multiMatchQuery("duanxz-pc", "instanceName", "ip")); //精确匹配
			
			//query.must(QueryBuilders.matchQuery("message.operDesc", "删除资料"));
			
			query.must(QueryBuilders.queryStringQuery("*文*"));
			
			/*NestedQueryBuilder nestBuilder = QueryBuilders.nestedQuery("message", 
			        QueryBuilders.boolQuery() 
                    .must(QueryBuilders.matchQuery("message.busiType2", "市场员工").minimumShouldMatch("1")), ScoreMode.Total);*/
			
			SearchRequestBuilder requetBuilder = client.prepareSearch(database.getIndex()).setTypes(database.getTable()).setQuery(query);
			requetBuilder.setFrom(0); //从第几条开始
			requetBuilder.setSize(10);  //查询多少条
			requetBuilder.addSort(SortBuilders.fieldSort("timestamps").order(SortOrder.DESC));
			SearchResponse response = requetBuilder.execute().actionGet();
			if (response != null && response.getHits() != null) {
				 System.out.println(JSONObject.toJSONString(response.getHits()));
	        	 log.info("startPage:" + startPage + ",pageSize=" + pageSize + ",totalCount=" + response.getHits().getTotalHits());
	        	 for (SearchHit searchHit:response.getHits()) {
	        		 retList.add(searchHit.getSourceAsMap());
	 	        }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		return retList;
	}
	
	
}
