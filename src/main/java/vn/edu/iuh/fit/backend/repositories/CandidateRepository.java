package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.backend.models.Address;
import vn.edu.iuh.fit.backend.models.Candidate;

import java.time.LocalDate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Transactional
    @Modifying
    @Query("update Candidate c set c.fullName = ?1, c.dob = ?2, c.phone = ?3, c.email = ?4 where c.id = ?5")
    int updateFullNameAndDobAndPhoneAndEmailById(String fullName, LocalDate dob, String phone, String email, long id);


}