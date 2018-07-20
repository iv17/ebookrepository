package rs.ac.uns.ftn.udd.ebookrepositoryserver.converters;

import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.ResultData;

@Component
public class ESConverter {

	
	public ResultData convert(IndexUnit index) {
		ResultData result = new ResultData();
		result.setFilename(index.getFilename());
		result.setText(index.getText());
		result.setTitle(index.getTitle());
		result.setAuthor(index.getAuthor());
		result.setKeywords(index.getKeywords());
		result.setMime(index.getMime());
		result.setPublicationYear(index.getPublicationYear());
		result.setLanguageName(index.getLanguageName());
		result.setCategoryName(index.getCategoryName());
		result.setHighlight(index.getHightlight());
		return result;
	}
	
}
