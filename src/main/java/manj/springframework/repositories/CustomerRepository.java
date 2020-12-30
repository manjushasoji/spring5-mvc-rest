package manj.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import manj.springframework.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
