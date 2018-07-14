package rs.ac.uns.ftn.udd.ebookrepositoryserver.converters;

import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Language;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LanguageDTO;

@Component
public class LanguageConverter {

	public Language convert(LanguageDTO dto) {
		Language language = new Language();
		language.setId(dto.getId());
		language.setName(dto.getName());
		return language;
	}
	
	public LanguageDTO convert(Language language) {
		LanguageDTO dto = new LanguageDTO();
		dto.setId(language.getId());
		dto.setName(language.getName());
		return dto;
	}
	
}
