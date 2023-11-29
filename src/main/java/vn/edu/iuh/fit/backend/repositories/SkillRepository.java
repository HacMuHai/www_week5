package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.backend.models.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("select s from Skill s where s.id not in (select cs.skill.id from CandidateSkill cs where cs.candidate.id = :id)")
    Page<Skill> findByCandidateIdNot(long id, Pageable pageable);

    @Query("select s from Skill s where s.id in (select cs.skill.id from CandidateSkill cs where cs.candidate.id = :id)")
    Page<Skill> findByCandidateId(long id, Pageable pageable);
}