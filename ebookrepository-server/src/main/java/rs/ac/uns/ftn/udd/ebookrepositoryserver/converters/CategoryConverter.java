package rs.ac.uns.ftn.udd.ebookrepositoryserver.converters;

import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.CategoryDTO;

@Component
public class CategoryConverter {

	public Category convert(CategoryDTO dto) {
		Category category = new Category();
		category.setId(dto.getId());
		category.setName(dto.getName());
		return category;
	}
	
	public CategoryDTO convert(Category category) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(category.getId());
		dto.setName(category.getName());
		return dto;
	}
	
}
