package app.core.repositories;

import org.springframework.data.repository.CrudRepository;

import app.core.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
