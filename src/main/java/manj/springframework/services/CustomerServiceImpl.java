package manj.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import manj.springframework.api.v1.mapper.CustomerMapper;
import manj.springframework.api.v1.model.CustomerDTO;
import manj.springframework.api.v1.model.CustomerListDTO;
import manj.springframework.controllers.v1.CustomerController;
import manj.springframework.domain.Customer;
import manj.springframework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {

		return customerRepository.findAll().stream().map(customer -> {
			CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
			customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
			return customerDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO).map(customerDTO -> {
			// set API URL
			customerDTO.setCustomerUrl(getCustomerUrl(id));
			return customerDTO;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	private String getCustomerUrl(Long id) {
		return CustomerController.BASE_URL + "/" + id;
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

		return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
	}

	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);

		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);

		returnDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

		return returnDto;
	}

	@Override
	public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		customer.setId(id);

		return saveAndReturnDTO(customer);

	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {

		return customerRepository.findById(id).map(customer -> {
			if (customerDTO.getFirstname() != null) {
				customer.setFirstname(customerDTO.getFirstname());
			}

			if (customerDTO.getLastname() != null) {
				customer.setLastname(customerDTO.getLastname());
			}
			CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			returnDTO.setCustomerUrl(getCustomerUrl(id));
			return returnDTO;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);

	}

}
