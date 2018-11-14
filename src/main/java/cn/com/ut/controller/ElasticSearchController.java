package cn.com.ut.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.ut.pojo.GoodsIndexQueryVo;
import cn.com.ut.service.GoodsService;
import cn.com.ut.util.ElasticSearchUtil;
import cn.com.ut.vo.EsIndex;
import cn.com.ut.vo.EsIndexField;
import cn.com.ut.vo.EsSearchField;
import cn.com.ut.vo.EsSearchFields;

/**
 * 
 * @author wangpeng1
 * @since 2018年10月23日
 */
@RestController
@RequestMapping("/es")
public class ElasticSearchController {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private GoodsService goodsService;

	/**
	 * 获取IK分词结果
	 * 
	 * @param searchContent
	 * @return
	 */
	@GetMapping("/getIkTerms")
	public List<String> add(@RequestParam(name = "searchContent") String searchContent) {

		return getIkAnalyzeSearchTerms(searchContent);
	}

	private List<String> getIkAnalyzeSearchTerms(String searchContent) {

		// 调用IK分词器进行分词
		AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(
				elasticsearchTemplate.getClient(), AnalyzeAction.INSTANCE, "demo", searchContent);
		ikRequest.setTokenizer("ik_max_word");
		List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet()
				.getTokens();

		// 循环赋值
		List<String> searchTermList = new ArrayList<String>();
		ikTokenList.forEach(ikToken -> {
			searchTermList.add(ikToken.getTerm());
		});
		return searchTermList;
	}

	@GetMapping("/createIndex")
	public void createIndex(@RequestParam String indexName) {

		ElasticSearchUtil.createIndex(indexName);
	}

	@PostMapping("/createType")
	public void createType(@RequestBody EsIndexField index) {

		ElasticSearchUtil.createType(index.getIndex(), index.getType(), index.getFieldList());
	}

	@GetMapping("/deleteIndex")
	public String deleteIndex(@RequestParam String indexName) {

		return ElasticSearchUtil.deleteIndex(indexName);
	}

	@PostMapping("/insertData")
	public String insertData(@RequestBody EsIndex index) {

		return ElasticSearchUtil.insertData(index.getIndex(), index.getType(), index.getContent());
	}

	@PostMapping("/updateData")
	public String updateData(@RequestBody EsIndex index) {

		return ElasticSearchUtil.updateData(index.getIndex(), index.getType(), index.getId(),
				index.getContent());
	}

	@PostMapping("/deleteData")
	public String deleteData(@RequestBody EsIndex index) {

		return ElasticSearchUtil.deleteData(index.getIndex(), index.getType(), index.getId());
	}

	@PostMapping("/matchAllQuery")
	public List<Map<String, Object>> matchAllQuery() {

		return ElasticSearchUtil.matchAllQuery();
	}

	@PostMapping("/matchQuery")
	public List<Map<String, Object>> matchQuery(@RequestBody EsSearchField searchField) {

		return ElasticSearchUtil.matchQuery(searchField.getFieldName(), searchField.getKeyword());
	}

	@PostMapping("/matchMultiQuery")
	public List<Map<String, Object>> matchMultiQuery(@RequestBody EsSearchFields searchFields) {

		return ElasticSearchUtil.matchMultiQuery(searchFields.getFieldNames(),
				searchFields.getKeyword());
	}

	@PostMapping("/queryString")
	public List<Map<String, Object>> queryString(@RequestBody EsSearchField searchField) {

		return ElasticSearchUtil.queryString(searchField.getKeyword());
	}

	@PostMapping("/termQuery")
	public List<Map<String, Object>> termQuery(@RequestBody EsSearchField searchField) {

		return ElasticSearchUtil.termQuery(searchField.getFieldName(), searchField.getKeyword());

	}

	@PostMapping("/fuzzyQuery")
	public List<Map<String, Object>> fuzzyQuery(@RequestBody EsSearchField searchField) {

		return ElasticSearchUtil.fuzzyQuery(searchField.getFieldName(), searchField.getKeyword());

	}

	@PostMapping("/wildcardQuery")
	public List<Map<String, Object>> wildcardQuery(@RequestBody EsSearchField searchField) {

		return ElasticSearchUtil.wildcardQuery(searchField.getFieldName(),
				searchField.getKeyword());
	}

	@PostMapping("/searchScrollFunction")
	public List<Map<String, Object>> searchScrollFunction() throws Exception {

		return ElasticSearchUtil.searchScrollFunction();
	}

	@GetMapping("/searchByScrollId")
	public List<Map<String, Object>> searchByScrollId(@RequestParam String scrollId)
			throws Exception {

		return ElasticSearchUtil.searchByScrollId(scrollId);
	}

	@GetMapping("/importGoodsData")
	public List<Map<String, Object>> importGoodsData(@RequestParam String index,
			@RequestParam String type) throws Exception {

		return goodsService.importGoodsData(index, type);
	}

	@PostMapping("/queryIndex")
	public List<Map<String, Object>> queryIndex(@RequestBody GoodsIndexQueryVo goodsIndexQueryVo)
			throws Exception {

		return goodsService.queryIndex(goodsIndexQueryVo);
	}
}
