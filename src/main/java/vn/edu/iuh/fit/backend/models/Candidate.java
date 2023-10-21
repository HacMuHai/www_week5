package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidate")
@Data
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "can_id")
    private long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "dob", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @OneToOne(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    @Column(name = "phone", length = 15, nullable = false, unique = true)
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<CandidateSkill> candidateSkills;

    public Candidate(long id) {
        this.id = id;
    }

    public Candidate(String fullName, LocalDate dob, Address address, String phone, String email) {
        this.fullName = fullName;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public void setCandidateSkills(List<CandidateSkill> candidateSkills) {
        this.candidateSkills = candidateSkills;
    }
}
