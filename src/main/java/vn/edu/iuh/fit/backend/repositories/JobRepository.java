package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.backend.models.Job;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("select distinct j from Job j, JobSkill js, CandidateSkill ck" +
            " where j.id = js.job.id and js.skill.id = ck.skill.id AND ck.candidate.id = :canId" +
            " AND js.skillLevel >= ck.skillLevel " +
            " AND ck.candidate.address.city = j.company.address.city AND ck.candidate.address.country = j.company.address.country")
    Page<Job> findJobsByCanId(long canId, Pageable pageable);

    List<Job> findByCompany_Id(long id);


    @Override
    Optional<Job> findById(Long aLong);
}