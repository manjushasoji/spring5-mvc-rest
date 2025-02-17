package manj.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import manj.springframework.api.v1.mapper.CategoryMapper;
import manj.springframework.api.v1.model.CategoryDTO;
import manj.springframework.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {

		return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {

		return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name));
	}

}
