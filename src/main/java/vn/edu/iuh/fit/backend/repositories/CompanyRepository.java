package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.backend.models.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}