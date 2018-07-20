package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
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
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.handlers.DocumentHandler;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.handlers.PDFHandler;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.RequiredHighlight;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.ResultData;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.repository.IndexUnitRepository;

@Service
public class ResultRetriever {

	@Autowired
	private IndexUnitRepository repository;

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	public ResultRetriever(){
	}
	
	public List<ResultData> getResultsWithHighlight(org.elasticsearch.index.query.QueryBuilder query) {
		
		SearchQuery sq = new NativeSearchQueryBuilder()
				.withQuery(query)
				.withHighlightFields(
						new HighlightBuilder.Field("content"), 
						new HighlightBuilder.Field("title"), 
						new HighlightBuilder.Field("keywords"), 
						new HighlightBuilder.Field("author"),
						new HighlightBuilder.Field("language"))
				.build();
		
		Page<IndexUnit> booksEntities = elasticsearchTemplate.queryForPage(sq, IndexUnit.class, new SearchResultMapper() {

			@SuppressWarnings("unchecked")
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
							Text [] text = searchHit.getHighlightFields().get("content").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("author") != null){
							Text [] text = searchHit.getHighlightFields().get("author").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("title") != null){
							Text [] text = searchHit.getHighlightFields().get("title").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("keywords") != null){
							Text [] text = searchHit.getHighlightFields().get("keywords").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
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
		if(booksEntities != null){
			
			for (IndexUnit index : booksEntities) {
				ResultData resultData = new ResultData();
				resultData.setAuthor(index.getAuthor());
				resultData.setTitle(index.getTitle());
				resultData.setKeywords(index.getKeywords());
				resultData.setHighlight(index.getHightlight());
				response.add(resultData);
			}
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

	protected DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}else{
			return null;
		}
	}
}
