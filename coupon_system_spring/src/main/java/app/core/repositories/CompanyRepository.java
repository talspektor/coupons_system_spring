package app.core.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import app.core.entities.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

	boolean existsByEmailAndPassword(String email, String password);
	
	Optional<Company> findByEmailAndPassword(String email, String password);
}
