package manj.springframework.services;

import java.util.List;

import manj.springframework.api.v1.model.CategoryDTO;

public interface CategoryService {
	
	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryByName(String name);
}
