package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.backend.ids.CandidateSkillID;
import vn.edu.iuh.fit.backend.models.CandidateSkill;

public interface CandidateSkillRepository extends CrudRepository<CandidateSkill, CandidateSkillID> {
}