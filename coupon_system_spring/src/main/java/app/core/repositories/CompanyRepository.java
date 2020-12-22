package app.core.repositories;

import org.springframework.data.repository.CrudRepository;

import app.core.entities.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}
