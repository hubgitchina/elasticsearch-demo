package cn.com.ut.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.ClearScrollRequestBuilder;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import cn.com.ut.vo.EsField;

public class ElasticSearchUtil {

	// private static Client client = EsServerClient.getClient();

	/**
	 * 判断是否存在该索引
	 * 
	 * @param indexName
	 *            索引名称
	 * @return
	 */
	public static boolean isIndexExists(String indexName) {

		Client client = EsServerClient.getClient();
		IndicesExistsRequestBuilder builder = client.admin().indices().prepareExists(indexName);
		IndicesExistsResponse res = builder.get();
		client.close();
		return res.isExists();
	}

	/**
	 * 创建索引
	 * 
	 * @param indexName
	 */
	public static void createIndex(String indexName) {

		if (isIndexExists(indexName)) {
			throw new RuntimeException("索引对象已经存在，无法创建！");
		}

		Client client = EsServerClient.getClient();
		client.admin().indices().prepareCreate(indexName).execute().actionGet();
		client.close();
	}

	/**
	 * 创建索引
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引type
	 * @param sourcecontent
	 *            要索引的内容
	 */
	public static void createIndex(String index, String type, String sourcecontent) {

		if (isIndexExists(index)) {
			throw new RuntimeException("索引对象已经存在，无法创建！");
		}

		Client client = EsServerClient.getClient();
		IndexResponse response = client.prepareIndex(index, type).setSource(sourcecontent).execute()
				.actionGet();
		printIndexInfo(response);
		client.close();
	}

	public static void createType(String index, String type, List<EsField> fieldList) {

		Client client = EsServerClient.getClient();

		PutMappingRequest mapping = Requests.putMappingRequest(index).type(type)
				.source(getMapping(fieldList));
		client.admin().indices().putMapping(mapping).actionGet();

		client.close();
	}

	public static XContentBuilder getMapping(List<EsField> fieldList) {

		XContentBuilder mapping = null;
		try {
			mapping = XContentFactory.jsonBuilder().startObject().startObject("properties");

			for (EsField field : fieldList) {
				mapping.startObject(field.getFieldName());
				if (!"".equals(field.getType()) && field.getType() != null) {
					mapping.field("type", field.getType());
				}

				if (!"".equals(field.getIndex()) && field.getIndex() != null) {
					mapping.field("index", field.getIndex());
				}

				if (!"".equals(field.getAnalyzer()) && field.getAnalyzer() != null) {
					mapping.field("analyzer", field.getAnalyzer());
				}

				if (!"".equals(field.getSearchAnalyzer()) && field.getSearchAnalyzer() != null) {
					mapping.field("searchAnalyzer", field.getSearchAnalyzer());
				}

				if (!"".equals(field.getFormat()) && field.getFormat() != null) {
					mapping.field("format", field.getFormat());
				}

				mapping.endObject();
			}

			mapping.endObject().endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mapping;
	}

	public static String deleteIndex(String indexName) {

		try {
			if (!isIndexExists(indexName)) {
				throw new RuntimeException("索引对象已经不存在，无法删除！");
			}
			Client client = EsServerClient.getClient();

			DeleteIndexRequestBuilder builder = client.admin().indices().prepareDelete(indexName);

			DeleteIndexResponse res = builder.get();

			client.close();
			if (res.isAcknowledged()) {
				return "删除索引成功！";
			} else {
				return "删除索引失败！";
			}
		} catch (Exception e) {
			throw new RuntimeException("删除索引失败！");
		} finally {

		}
	}

	public static String insertData(String index, String type, JSONObject data) {

		Client client = EsServerClient.getClient();

		// for (int i = 0; i < 1000; i++) {
		// JSONObject tempData = new JSONObject();
		// tempData.put("id", 1000 + i);
		// tempData.put("appName", "appName:" + i);
		// tempData.put("appDesc", "appDesc:" + i);
		// client.prepareIndex(index, type).setSource(tempData).get();
		// }
		IndexResponse response = client.prepareIndex(index, type).setSource(data).get();

		printIndexInfo(response);

		client.close();

		return "插入数据成功";
	}

	public static String bulkInsertData(String index, String type, JSONArray batchData,
			String idField) {

		Client client = EsServerClient.getClient();

		BulkRequestBuilder bulkRequest = client.prepareBulk();

		for (int i = 0; i < batchData.size(); i++) {
			JSONObject data = batchData.getJSONObject(i);

			String id = data.getString(idField);

			bulkRequest.add(client.prepareIndex(index, type).setId(id).setSource(data));
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			BulkItemResponse[] bulkItemResponse = bulkResponse.getItems();
			for (int i = 0; i < bulkItemResponse.length; i++) {
				System.out.println(
						bulkItemResponse[i].getItemId() + ":" + bulkItemResponse[i].getIndex() + ":"
								+ bulkItemResponse[i].getFailureMessage());
			}
		}

		client.close();

		return "批量插入数据成功";
	}

	public static String updateData(String index, String type, String id, JSONObject data) {

		Client client = EsServerClient.getClient();

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(id);
		updateRequest.doc(data);
		try {
			UpdateResponse updateResponse = client.update(updateRequest).get();

			printIndexInfo(updateResponse);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		return "更新数据成功";
	}

	public static String deleteData(String index, String type, String id) {

		Client client = EsServerClient.getClient();

		DeleteResponse response = client.prepareDelete(index, type, id).get();

		printIndexInfo(response);

		client.close();

		return "删除数据成功";
	}

	/**
	 * 打印插入索引信息
	 * 
	 * @param response
	 */
	private static void printIndexInfo(IndexResponse response) {

		System.out.println("**************** insert ***********************");
		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will
		// get: 1)
		long _version = response.getVersion();
		System.out.println(_index + "," + _type + "," + _id + "," + _version);
	}

	/**
	 * 打印更新索引信息
	 * 
	 * @param response
	 */
	private static void printIndexInfo(UpdateResponse response) {

		System.out.println("**************** update ***********************");
		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will
		// get: 1)
		long _version = response.getVersion();
		System.out.println(_index + "," + _type + "," + _id + "," + _version);
	}

	/**
	 * 打印删除索引信息
	 * 
	 * @param response
	 */
	private static void printIndexInfo(DeleteResponse response) {

		System.out.println("**************** delete ***********************");
		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will
		// get: 1)
		long _version = response.getVersion();
		System.out.println(_index + "," + _type + "," + _id + "," + _version);
	}

	public static List<Map<String, Object>> matchAllQuery() {

		Client client = EsServerClient.getClient();

		SearchResponse searchResponse = client.prepareSearch()
				.setQuery(QueryBuilders.matchAllQuery())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				// 设置查询数据的位置,分页用
				.setFrom(0)
				// 设置查询结果集的最大条数
				.setSize(60)
				// 设置是否按查询匹配度排序
				.setExplain(true).execute().actionGet();

		SearchHits searchHits = searchResponse.getHits();

		System.out.println("----------------- matchAllQuery ---------------------");
		System.out.println("共匹配到:" + searchHits.getTotalHits() + "条记录!");

		client.close();

		if (searchHits.getTotalHits() > 0) {
			List<Map<String, Object>> resultList = Lists
					.newArrayListWithCapacity((int) searchHits.getTotalHits());
			for (SearchHit searchHit : searchHits) {
				Map<String, Object> sourceAsMap = searchHit.getSource();
				resultList.add(sourceAsMap);
			}
			return resultList;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * 单个字段IK查找 (matchQuery会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到)
	 * 
	 * @param searchField
	 * @param keyword
	 * @return
	 */
	public static List<Map<String, Object>> matchQuery(String searchField, String keyword) {

		QueryBuilder queryBuilder = QueryBuilders.matchQuery(searchField, keyword);
		return searchFunction(queryBuilder);
	}

	public static List<Map<String, Object>> matchMultiQuery(String[] searchFields, String keyword) {

		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, searchFields);
		return searchFunction(queryBuilder);
	}

	public static List<Map<String, Object>> boolQuery(BoolQueryBuilder boolQueryBuilder, int pageno,
			int pagesize, SortBuilder sortBuilder) {

		return searchFunction(boolQueryBuilder, pageno, pagesize, sortBuilder);
	}

	public static List<Map<String, Object>> queryString(String keyword) {

		StringBuilder sb = new StringBuilder();
		sb.append("*").append(keyword).append("*");
		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(sb.toString()); // 通配符查询
		// QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("我的.+f");
		return searchFunction(queryBuilder);
	}

	/**
	 * 单个字段精确查找 (不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到)
	 * 
	 * @param searchField
	 * @param keyword
	 * @return
	 */
	public static List<Map<String, Object>> termQuery(String searchField, String keyword) {

		QueryBuilder queryBuilder = QueryBuilders.termQuery(searchField, keyword);
		return searchFunction(queryBuilder);
	}

	/**
	 * 单个字段模糊查询
	 * 
	 * @param searchField
	 * @param keyword
	 * @return
	 */
	public static List<Map<String, Object>> fuzzyQuery(String searchField, String keyword) {

		QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery(searchField, keyword);
		return searchFunction(queryBuilder);
	}

	/**
	 * 通配符查询
	 * 
	 * @param searchField
	 * @param keyword
	 * @return
	 */
	public static List<Map<String, Object>> wildcardQuery(String searchField, String keyword) {

		StringBuilder sb = new StringBuilder();
		sb.append("*").append(keyword).append("*");
		QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(searchField, sb.toString());
		return searchFunction(queryBuilder);
	}

	/**
	 * scroll分页
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> searchScrollFunction() {

		Client client = EsServerClient.getClient();

		SearchResponse scrollResp = client.prepareSearch().setIndices("mall").setTypes("mallapp")
				.addSort("id", SortOrder.ASC).setScroll(new TimeValue(60000))
				.setQuery(QueryBuilders.matchAllQuery()).setSize(10).execute().actionGet();

		List<Map<String, Object>> resultList = Lists.newArrayList();

		// 获取总数量
		long totalCount = scrollResp.getHits().getTotalHits();
		// 计算总页数,每次搜索数量为分片数*设置的size大小
		// int page = (int) totalCount / (5 * 10);

		int page = (int) totalCount / 10;

		System.out.println("总记录数：" + totalCount);
		System.out.println("总页数：" + page);

		String scrollId = scrollResp.getScrollId();
		System.out.println("scrollId: " + scrollId);

		if (scrollResp.getHits().getTotalHits() > 0) {
			for (SearchHit searchHit : scrollResp.getHits()) {
				Map<String, Object> sourceMap = searchHit.getSource();
				resultList.add(sourceMap);
			}
		}

		// int flag = 1;
		// while (true) {
		// for (SearchHit searchHit : scrollResp.getHits()) {
		// Map<String, Object> sourceMap = searchHit.getSource();
		// resultList.add(sourceMap);
		// }
		//
		// System.out.println(flag + " scrollId: " + scrollResp.getScrollId());
		// flag++;
		//
		// scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
		// .setScroll(new TimeValue(600000)).execute().actionGet();
		// if (scrollResp.getHits().getHits().length == 0) {
		// break;
		// }
		// }

		client.close();

		return resultList;
	}

	/**
	 * 根据scrollId查询，每次调用只要scrollId在有效期内，则都会自动查询下一页的数据
	 * 
	 * @param scrollId
	 * @return
	 */
	public static List<Map<String, Object>> searchByScrollId(String scrollId) {

		Client client = EsServerClient.getClient();

		// 再次发送请求,并使用上次搜索结果的ScrollId
		SearchResponse scrollResp = client.prepareSearchScroll(scrollId)
				.setScroll(new TimeValue(60000)).execute().actionGet();

		List<Map<String, Object>> resultList = Lists.newArrayList();

		if (scrollResp.getHits().getTotalHits() > 0) {
			for (SearchHit searchHit : scrollResp.getHits()) {
				Map<String, Object> sourceMap = searchHit.getSource();
				resultList.add(sourceMap);
			}
		}

		client.close();

		return resultList;
	}

	/**
	 * 清除滚动ID
	 * 
	 * @param client
	 * @param scrollIdList
	 * @return
	 */
	public static boolean clearScroll(Client client, List<String> scrollIdList) {

		ClearScrollRequestBuilder clearScrollRequestBuilder = client.prepareClearScroll();
		clearScrollRequestBuilder.setScrollIds(scrollIdList);
		ClearScrollResponse response = clearScrollRequestBuilder.get();
		return response.isSucceeded();
	}

	/**
	 * 清除滚动ID
	 * 
	 * @param client
	 * @param scrollId
	 * @return
	 */
	public static boolean clearScroll(Client client, String scrollId) {

		ClearScrollRequestBuilder clearScrollRequestBuilder = client.prepareClearScroll();
		clearScrollRequestBuilder.addScrollId(scrollId);
		ClearScrollResponse response = clearScrollRequestBuilder.get();
		return response.isSucceeded();
	}

	/**
	 * 根据from-size进行分页查询（默认分页深度的10000，如果超过10000就会报错）
	 * 
	 * @param queryBuilder
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	private static List<Map<String, Object>> searchFunction(BoolQueryBuilder boolQueryBuilder,
			int pageno, int pagesize, SortBuilder sortBuilder) {

		Client client = EsServerClient.getClient();

		int from = (pageno - 1) * pagesize;

		SearchResponse response = null;
		if (sortBuilder != null) {
			response = client.prepareSearch().setQuery(boolQueryBuilder).setFrom(from)
					.setSize(pagesize).addSort(sortBuilder).execute().actionGet();
		} else {
			response = client.prepareSearch().setQuery(boolQueryBuilder).setFrom(from)
					.setSize(pagesize).execute().actionGet();
		}

		SearchHits searchHits = response.getHits();

		client.close();

		if (searchHits.getTotalHits() > 0) {
			List<Map<String, Object>> resultList = Lists
					.newArrayListWithCapacity((int) searchHits.getTotalHits());
			for (SearchHit searchHit : searchHits) {
				Map<String, Object> map = searchHit.getSource();
				resultList.add(map);
			}
			return resultList;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	private static List<Map<String, Object>> searchFunction(QueryBuilder queryBuilder) {

		Client client = EsServerClient.getClient();

		SearchResponse response = client.prepareSearch().setQuery(queryBuilder).setFrom(0)
				.setSize(100).execute().actionGet();
		SearchHits searchHits = response.getHits();

		client.close();

		if (searchHits.getTotalHits() > 0) {
			List<Map<String, Object>> resultList = Lists
					.newArrayListWithCapacity((int) searchHits.getTotalHits());
			for (SearchHit searchHit : searchHits) {
				Map<String, Object> map = searchHit.getSource();
				resultList.add(map);
			}
			return resultList;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	private List<Map<String, Object>> searchFunction(QueryBuilder queryBuilder, String[] indices,
			String[] types) {

		Client client = EsServerClient.getClient();

		SearchRequestBuilder requestBuilder = client.prepareSearch().setIndices(indices)
				.setTypes(types).setScroll(new TimeValue(60000)).setQuery(queryBuilder);
		SearchResponse response = requestBuilder.setFrom(0).setSize(100).execute().actionGet();
		SearchHits searchHits = response.getHits();
		SearchHit[] hits = searchHits.getHits();

		client.close();

		if (hits.length > 0) {
			List<Map<String, Object>> resultList = Lists.newArrayListWithCapacity(hits.length);
			for (SearchHit searchHit : hits) {
				Map<String, Object> sourceAsMap = searchHit.sourceAsMap();
				resultList.add(sourceAsMap);
			}
			return resultList;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	public static void main(String[] args) throws Exception {

		// createMapping("lianan", "lianan");
		// createCluterName("lianan");
	}

}
