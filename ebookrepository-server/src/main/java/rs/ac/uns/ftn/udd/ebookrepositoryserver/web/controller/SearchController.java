package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.EBookConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.analysers.SerbianAnalyzer;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.AdvancedQuery;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.RequiredHighlight;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.ResultData;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.SearchType;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.SimpleQuery;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.search.QueryBuilder;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.search.ResultRetriever;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.EBookDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SearchController {

	@Autowired
	private ResultRetriever resultRetriever;

	@Autowired
	private EBookConverter eBookConverter;


	@PostMapping(value="/search/term", consumes="application/json")
	public ResponseEntity<List<EBookDTO>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {	
		String text = SerbianAnalyzer.analize(simpleQuery.getValue());
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), text);
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), text));
		List<ResultData> results = resultRetriever.getResults(query, rh);		
		List<EBookDTO> ebooks = convert(results);
		return new ResponseEntity<List<EBookDTO>>(ebooks, HttpStatus.OK);
	}

	@PostMapping(value="/search/fuzzy", consumes="application/json")
	public ResponseEntity<List<EBookDTO>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
		String text = SerbianAnalyzer.analize(simpleQuery.getValue());
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), text);
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), text));
		List<ResultData> results = resultRetriever.getResults(query, rh);		
		List<EBookDTO> ebooks = convert(results);
		return new ResponseEntity<List<EBookDTO>>(ebooks, HttpStatus.OK);
	}

	@PostMapping(value="/search/prefix", consumes="application/json")
	public ResponseEntity<List<EBookDTO>> searchPrefix(@RequestBody SimpleQuery simpleQuery) throws Exception {
		String text = SerbianAnalyzer.analize(simpleQuery.getValue());
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.prefix, simpleQuery.getField(), text);
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), text));
		List<ResultData> results = resultRetriever.getResults(query, rh);		
		List<EBookDTO> ebooks = convert(results);
		return new ResponseEntity<List<EBookDTO>>(ebooks, HttpStatus.OK);
	}

	@PostMapping(value="/search/range", consumes="application/json")
	public ResponseEntity<List<EBookDTO>> searchRange(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = resultRetriever.getResults(query, rh);	
		List<EBookDTO> ebooks = convert(results);
		return new ResponseEntity<List<EBookDTO>>(ebooks, HttpStatus.OK);
	}

	@PostMapping(value="/search/phrase", consumes="application/json")
	public ResponseEntity<List<EBookDTO>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
		String text = SerbianAnalyzer.analize(simpleQuery.getValue());
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), text);
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), text));
		List<ResultData> results = resultRetriever.getResults(query, rh);
		List<EBookDTO> ebooks = convert(results);
		return new ResponseEntity<List<EBookDTO>>(ebooks, HttpStatus.OK);
	}

	@PostMapping(value="/search/boolean", consumes="application/json")
	public ResponseEntity<List<EBookDTO>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
		String text1 = SerbianAnalyzer.analize(advancedQuery.getValue1());
		String text2 = SerbianAnalyzer.analize(advancedQuery.getValue2());
		org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), text1);
		org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), text2);

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
		rh.add(new RequiredHighlight(advancedQuery.getField1(), text1));
		rh.add(new RequiredHighlight(advancedQuery.getField2(), text2));
		List<ResultData> results = resultRetriever.getResultsWithHighlight(builder);
		List<EBookDTO> ebooks = convert(results);
		return new ResponseEntity<List<EBookDTO>>(ebooks, HttpStatus.OK);

	}

	@PostMapping(value="/search/queryParser", consumes="application/json")
	public ResponseEntity<List<EBookDTO>> search(@RequestBody SimpleQuery simpleQuery) throws Exception {
		String text = SerbianAnalyzer.analize(simpleQuery.getValue());
		
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), text);
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), text));
		List<ResultData> results = resultRetriever.getResultsWithHighlight(query);
		List<EBookDTO> ebooks = convert(results);
		return new ResponseEntity<List<EBookDTO>>(ebooks, HttpStatus.OK);
	}

	public List<EBookDTO> convert(List<ResultData> results) {
		List<EBookDTO> ebooks = new ArrayList<>();
		for (ResultData resultData : results) {
			if(resultData != null) {
				EBookDTO dto = eBookConverter.convert(resultData);
				ebooks.add(dto);
			}
			
		}
		return ebooks;
	}

}
