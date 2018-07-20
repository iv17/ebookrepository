package rs.ac.uns.ftn.udd.ebookrepositoryserver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.ResultData;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.CategoryService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.EBookService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.LanguageService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.UserService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.EBookDTO;

@Component
public class EBookConverter {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EBookService eBookService;
	
	public EBook convert(EBookDTO dto) {
		EBook eBook = new EBook();
		
		eBook.setId(dto.getId());
		eBook.setTitle(dto.getTitle());
		eBook.setAuthor(dto.getAuthor());
		eBook.setFilename(dto.getFilename());
		eBook.setPublicationYear(dto.getPublicationYear());
		eBook.setKeywords(dto.getKeywords());
		eBook.setMime(dto.getMime());
		eBook.setCategory(categoryService.findByName(dto.getCategoryName()));
		eBook.setLanguage(languageService.findByName(dto.getLanguageName()));
		eBook.setCataloguer(userService.findByEmail(dto.getCataloguerName()));
		
		return eBook;
	}
	
	public EBookDTO convert(EBook eBook) {
		EBookDTO dto = new EBookDTO();
		
		dto.setId(eBook.getId());
		dto.setFilename(eBook.getFilename());
		dto.setTitle(eBook.getTitle());
		dto.setAuthor(eBook.getAuthor());
		dto.setPublicationYear(eBook.getPublicationYear());
		dto.setKeywords(eBook.getKeywords());
		dto.setMime(eBook.getMime());
		dto.setCategoryName(eBook.getCategory().getName());
		dto.setLanguageName(eBook.getLanguage().getName());
		dto.setCataloguerName(eBook.getCataloguer().getEmail());
		
		return dto;
	}
	
	public IndexUnit convert2I(EBookDTO dto) {
		IndexUnit index = new IndexUnit();
		
		index.setTitle(dto.getTitle());
		index.setAuthor(dto.getAuthor());
		index.setFilename(dto.getFilename());
		index.setKeywords(dto.getKeywords());
		index.setPublicationYear(dto.getPublicationYear());
		index.setLanguageName(dto.getLanguageName());
		index.setCategoryName(dto.getCategoryName());
		
		return index;
	}
	
	public EBookDTO convert(ResultData resultData) {
		EBook eBook = eBookService.findByFilename(resultData.getFilename());
		EBookDTO dto = convert(eBook);
		dto.setHighlight(resultData.getHighlight());
		dto.setText(resultData.getText());
		
		return dto;
	}
	
}
