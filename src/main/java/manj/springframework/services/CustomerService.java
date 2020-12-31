package manj.springframework.services;

import java.util.List;

import manj.springframework.api.v1.model.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();

	CustomerDTO getCustomerById(Long id);

	CustomerDTO createNewCustomer(CustomerDTO customerDTO);

	CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

	CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

	void deleteCustomerById(Long id);

}
