package manj.springframework.controllers.v1;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import manj.springframework.api.v1.model.CategoryDTO;
import manj.springframework.services.CategoryService;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {

	public static final String NAME = "Jim";

	@Mock
	CategoryService categoryService;

	@InjectMocks
	CategoryController categoryController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	void testGetallCategories() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1l);
		category1.setName(NAME);

		CategoryDTO category2 = new CategoryDTO();
		category2.setId(2l);
		category2.setName("Bob");

		List<CategoryDTO> categories = Arrays.asList(category1, category2);

		when(categoryService.getAllCategories()).thenReturn(categories);

		mockMvc.perform(get("/api/v1/categories/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("categories", hasSize(2)));

	}

	@Test
	void testGetCategoryByName() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1l);
		category1.setName(NAME);

		when(categoryService.getCategoryByName(anyString())).thenReturn(category1);

		mockMvc.perform(get("/api/v1/categories/Jim").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("name", equalTo(NAME)));
	}

}
