package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public ResultRetriever(){
	}

	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ResultData> results = new ArrayList<ResultData>();
       
        for (IndexUnit indexUnit : repository.search(query)) {
        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), "", indexUnit.getAuthor(), indexUnit.getLanguageName(), indexUnit.getCategoryName(), indexUnit.getPublicationYear()));
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
