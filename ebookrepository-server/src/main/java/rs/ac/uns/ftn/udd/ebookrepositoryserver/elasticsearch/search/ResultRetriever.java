package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.analysers.SerbianAnalyzer;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.handlers.DocumentHandler;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.handlers.PDFHandler;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.AdvancedQuery;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.RequiredHighlight;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.ResultData;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.SearchType;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.repository.IndexUnitRepository;

@Service
public class ResultRetriever {

	@Autowired
	private IndexUnitRepository repository;

	private static Analyzer analyzer = new SerbianAnalyzer();
	private PDFHandler pdfHandler = new PDFHandler();

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	public ResultRetriever(){
	}

	public List<ResultData> search(AdvancedQuery advancedQuery) throws IllegalArgumentException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

		org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
		org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());
		
		System.out.println(query1.toString());
		System.out.println(query2.toString());
		
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
			builder.must(query1);
			builder.must(query2);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
			builder.should(query1);
			builder.should(query2);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
			builder.must(query1);
			builder.mustNot(query2);
		}
		
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
		rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
		
		SearchQuery sq = new NativeSearchQueryBuilder()
				.withQuery(builder)
				.withHighlightFields(new HighlightBuilder.Field("content"), 
						new HighlightBuilder.Field("title"), 
						new HighlightBuilder.Field("keywords"), 
						new HighlightBuilder.Field("author"),
						new HighlightBuilder.Field("language"))
				.build();

		Page<IndexUnit> booksEntities = elasticsearchTemplate.queryForPage(sq, IndexUnit.class, new SearchResultMapper() {

			@Override
			public <T> AggregatedPage<T> mapResults(SearchResponse arg0, Class<T> arg1, Pageable arg2) {
				List<IndexUnit> books = new ArrayList<IndexUnit>();
				for(SearchHit searchHit : arg0.getHits()){
					if(arg0.getHits().getHits().length <= 0){
						return null;
					}

					IndexUnit resultData = new IndexUnit();
					resultData.setTitle((String) searchHit.getSource().get("title"));
					resultData.setKeywords((String) searchHit.getSource().get("keywords"));
					resultData.setAuthor((String) searchHit.getSource().get("author"));
				
					if(searchHit.getHighlightFields() != null){
						StringBuilder highlights = new StringBuilder("...");
						
						if(searchHit.getHighlightFields().get("content") != null){
							highlights.append(searchHit.getHighlightFields().get("content").fragments()[0].toString());
							highlights.append("...");
						}
					
						if(searchHit.getHighlightFields().get("title") != null){
							highlights.append(searchHit.getHighlightFields().get("title").fragments()[0].toString());
							highlights.append("...");
						}

						if(searchHit.getHighlightFields().get("keywords") != null){
							highlights.append(searchHit.getHighlightFields().get("keywords").fragments()[0].toString());
							highlights.append("...");
						}

						if(searchHit.getHighlightFields().get("author") != null){
							highlights.append(searchHit.getHighlightFields().get("author").fragments()[0].toString());
							highlights.append("...");
						}
						System.out.println(highlights.toString());
						System.out.println("----------------------");
						resultData.setHightlight(highlights.toString());
					}
					books.add(resultData);

				}

				if(books.size() > 0){
					return new AggregatedPageImpl<T>((List<T>) books);
				}

				return null;
			}
		});

		List<ResultData> response = new ArrayList<>();
		for (IndexUnit index : booksEntities) {
			ResultData resultData = new ResultData();
			resultData.setAuthor(index.getAuthor());
			resultData.setTitle(index.getTitle());
			resultData.setKeywords(index.getKeywords());
			resultData.setHighlight(index.getHightlight());
			response.add(resultData);
		}

		return response;

	}

	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}

		List<ResultData> results = new ArrayList<ResultData>();

		for (IndexUnit indexUnit : repository.search(query)) {
			results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), "", indexUnit.getAuthor(), indexUnit.getLanguageName(), indexUnit.getCategoryName(), indexUnit.getPublicationYear(), indexUnit.getFilename()));
		}


		return results;
	}

	public List<ResultData> getResultsWithHighlight (org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights, Query q) {
		if (query == null) {
			return null;
		}
		Highlighter highlighter = null;

		List<ResultData> results = new ArrayList<ResultData>();

		for (IndexUnit indexUnit : repository.search(query)) {
			results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), "", indexUnit.getAuthor(), indexUnit.getLanguageName(), indexUnit.getCategoryName(), indexUnit.getPublicationYear(), indexUnit.getFilename()));
		}

		for (ResultData resultData : results) {
			String highlighterString = "";

			for(RequiredHighlight requiredHighlight : requiredHighlights) {
				
				String fieldName = requiredHighlight.getFieldName();
				highlighter = new Highlighter(new QueryScorer(q, fieldName));
				String text = pdfHandler.getText(new File(resultData.getLocation()));
				try {
					String bestFragment = highlighter.getBestFragment(analyzer, fieldName, text);
					highlighterString += bestFragment;
					System.out.println(highlighterString);
					System.out.println("----------------------------------------");
				} catch (IOException | InvalidTokenOffsetsException e) {
					e.printStackTrace();
				}

			}
		}
		
		return results;
	}


	protected DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}else{
			return null;
		}
	}
}
