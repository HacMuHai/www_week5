package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.backend.models.Experience;

public interface ExperienceRepository extends CrudRepository<Experience, Long> {
}