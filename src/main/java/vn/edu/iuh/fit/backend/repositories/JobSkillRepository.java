package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.ids.JobSkillID;
import vn.edu.iuh.fit.backend.models.JobSkill;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillID> {
    List<JobSkill> findByJob_Id(long id);
}