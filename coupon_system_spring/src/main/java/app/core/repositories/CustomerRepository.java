package app.core.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import app.core.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	
	Optional<Customer> findByEmailAndPassword(String email, String password);
	
	boolean existsByEmail(String email);
	
	boolean existsByPassword(String password);
	
	Optional<Customer> findByEmail(String email);
}
