package manj.springframework.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.api.v1.model.CategoryDTO;
import manj.springframework.domain.Category;

class CategoryMapperTest {
	public static final String NAME = "Joe";
    public static final long ID = 1L;
	
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCategoryToCategoryDTO() {
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);
		
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
		
		assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
	}

}
