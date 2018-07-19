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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.analysers.SerbianAnalyzer;
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
	
	private static Analyzer analyzer = new SerbianAnalyzer();
	private PDFHandler pdfHandler = new PDFHandler();
	
	public ResultRetriever(){
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
        	String highlighterString = "";
    		
    		for(RequiredHighlight requiredHighlight : requiredHighlights) {
    			System.out.println(requiredHighlight.getFieldName() + " * " + requiredHighlight.getValue());
    			String fieldName = requiredHighlight.getFieldName();
    			highlighter = new Highlighter(new QueryScorer(q, fieldName));
    			String text = pdfHandler.getText(new File(indexUnit.getFilename()));
    			try {
    				String bestFragment = highlighter.getBestFragment(analyzer, fieldName, text);
    				highlighterString += bestFragment;
    				System.out.println(highlighterString);
    				System.out.println("----------------------------------------");
    			} catch (IOException | InvalidTokenOffsetsException e) {
    				e.printStackTrace();
    			}
    			
    		}
    		
        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), highlighterString, indexUnit.getAuthor(), indexUnit.getLanguageName(), indexUnit.getCategoryName(), indexUnit.getPublicationYear(), indexUnit.getFilename()));
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
