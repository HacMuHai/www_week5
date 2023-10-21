package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.backend.models.Skill;

public interface SkillRepository extends CrudRepository<Skill, Long> {
}