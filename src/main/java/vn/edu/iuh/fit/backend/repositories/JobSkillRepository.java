package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.backend.ids.JobSkillID;
import vn.edu.iuh.fit.backend.models.JobSkill;

public interface JobSkillRepository extends CrudRepository<JobSkill, JobSkillID> {
}