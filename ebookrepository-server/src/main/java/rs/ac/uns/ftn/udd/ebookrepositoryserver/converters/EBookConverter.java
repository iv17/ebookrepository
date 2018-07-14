package rs.ac.uns.ftn.udd.ebookrepositoryserver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.EBookDTO;

@Component
public class EBookConverter {

	@Autowired
	private LanguageConverter languageConverter;
	
	@Autowired
	private CategoryConverter categoryConverter;
	
	@Autowired
	private UserConverter userConverter;
	
	public EBook convert(EBookDTO dto) {
		EBook eBook = new EBook();
		eBook.setId(dto.getId());
		eBook.setTitle(dto.getTitle());
		eBook.setAuthor(dto.getAuthor());
		eBook.setFilename(dto.getFilename());
		eBook.setPublicationYear(dto.getPublicationYear());
		eBook.setMIME(dto.getMIME());
		eBook.setCategory(categoryConverter.convert(dto.getCategory()));
		eBook.setLanguage(languageConverter.convert(dto.getLanguage()));
		eBook.setCataloguer(userConverter.convert(dto.getCataloguer()));
		
		return eBook;
	}
	
	public EBookDTO convert(EBook eBook) {
		EBookDTO dto = new EBookDTO();
		dto.setId(eBook.getId());
		dto.setTitle(eBook.getTitle());
		dto.setAuthor(eBook.getAuthor());
		dto.setFilename(eBook.getFilename());
		dto.setPublicationYear(eBook.getPublicationYear());
		dto.setMIME(eBook.getMIME());
		dto.setCategory(categoryConverter.convert(eBook.getCategory()));
		dto.setLanguage(languageConverter.convert(eBook.getLanguage()));
		dto.setCataloguer(userConverter.convert(eBook.getCataloguer()));
		
		return dto;
	}
}
