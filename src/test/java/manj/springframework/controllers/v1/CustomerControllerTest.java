package manj.springframework.controllers.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import manj.springframework.api.v1.model.CustomerDTO;
import manj.springframework.controllers.RestResponseEntityExceptionHandler;
import manj.springframework.services.CustomerService;
import manj.springframework.services.ResourceNotFoundException;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest {

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	@Test
	void testGetListOfCustomers() throws Exception {
		// given
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Michale");
		customer1.setLastname("Weston");
		customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");
		customer2.setCustomerUrl(CustomerController.BASE_URL + "/2");

		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

		mockMvc.perform(get(CustomerController.BASE_URL).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	void testGetCustomerById() throws Exception {
		// given
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Michale");
		customer1.setLastname("Weston");
		customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");

		when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

		// when
		mockMvc.perform(get(CustomerController.BASE_URL + "/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstname", equalTo("Michale")));
	}

	@Test
	void testCreateNewCustomer() throws Exception {
		// given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");
		customer.setLastname("Flintstone");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

		when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

		// when/then
		mockMvc.perform(post(CustomerController.BASE_URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}

	@Test
	void testUpdateCustomer() throws Exception {
		// given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");
		customer.setLastname("Flintstone");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

		when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		// when/then
		mockMvc.perform(put(CustomerController.BASE_URL + "/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}

	@Test
	void testPatchCustomer() throws Exception {
		// given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname("Flintstone");
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

		when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
				.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(customer)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}

	@Test
	void testDeleteCustomer() throws Exception {
		mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(customerService).deleteCustomerById(anyLong());
	}

	@Test
	public void testNotFoundException() throws Exception {

		when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(CustomerController.BASE_URL + "/222")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
