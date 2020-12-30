package manj.springframework.services;

import java.util.List;

import manj.springframework.api.v1.model.CustomerDTO;

public interface CustomerService {
	
	List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

}
