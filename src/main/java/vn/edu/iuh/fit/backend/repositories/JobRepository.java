package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.backend.models.Job;

public interface JobRepository extends CrudRepository<Job, Long> {

}